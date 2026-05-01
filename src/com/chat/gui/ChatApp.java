package com.chat.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatApp extends Application {

    private TextArea chatArea;
    private TextField inputField;
    private TextField usernameField;

    private DataOutputStream dos;
    private DataInputStream dis;

    private Socket socket;

    @Override
    public void start(Stage stage) {

        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        Button connectBtn = new Button("Connect");
        Button sendBtn = new Button("Send");
        Button fileBtn = new Button("File");

        HBox top = new HBox(10, usernameField, connectBtn);
        HBox bottom = new HBox(10, inputField, sendBtn, fileBtn);

        VBox root = new VBox(10, top, chatArea, bottom);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 450, 500));
        stage.setTitle("Chat");
        stage.show();

        connectBtn.setOnAction(e -> connect());
        sendBtn.setOnAction(e -> send());
        fileBtn.setOnAction(e -> sendFile());
    }

    private void connect() {
        try {
            socket = new Socket("localhost", 5000);

            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());

            dos.writeUTF("USERNAME:" + usernameField.getText());

            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        String msg = dis.readUTF();
                        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> chatArea.appendText("Disconnected\n"));
                }
            });

            t.setDaemon(true);
            t.start();

        } catch (Exception e) {
            chatArea.appendText("Connection failed\n");
        }
    }

    private void send() {
        try {
            if (dos == null) return;

            String msg = inputField.getText();
            if (msg.isEmpty()) return;

            dos.writeUTF(msg);
            inputField.clear();

        } catch (Exception e) {
            chatArea.appendText("Send error\n");
        }
    }

    private void sendFile() {
        try {
            if (dos == null) return;

            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);

            if (file == null) return;

            FileInputStream fis = new FileInputStream(file);

            dos.writeUTF("FILE");
            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            int r;

            while ((r = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, r);
            }

            fis.close();

        } catch (Exception e) {
            chatArea.appendText("File error\n");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}