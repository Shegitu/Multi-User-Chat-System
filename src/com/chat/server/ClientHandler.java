package com.chat.server;

import com.chat.database.MessageDAO;
import com.chat.database.UserDAO;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String username = "User";
    private int userId = -1;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (Exception e) {
            close();
        }
    }

    @Override
    public void run() {

        MessageDAO messageDAO = new MessageDAO();
        UserDAO userDAO = new UserDAO();

        try {
            while (!socket.isClosed()) {

                String input = dis.readUTF();

                if (input.startsWith("USERNAME:")) {

                    username = input.substring(9);
                    userId = userDAO.getUser(username);

                } else if (input.equals("FILE")) {

                    receiveFile();

                } else {

                    String msg = username + ": " + input;

                    if (userId != -1) {
                        messageDAO.saveMessage(userId, userId, msg);
                    }

                    for (ClientHandler c : ChatServer.clients) {
                        c.sendMessage(msg);
                    }
                }
            }

        } catch (Exception e) {
            close();
        }
    }

    private void receiveFile() {

        try {
            String fileName = dis.readUTF();
            long size = dis.readLong();

            FileOutputStream fos = new FileOutputStream("server_" + fileName);

            byte[] buffer = new byte[4096];
            int r;
            long rem = size;

            while ((r = dis.read(buffer, 0, (int)Math.min(buffer.length, rem))) > 0) {
                fos.write(buffer, 0, r);
                rem -= r;
            }

            fos.close();

         for (ClientHandler client : ChatServer.clients) {
    if (client == this) {
        client.sendMessage("You sent file: " + fileName);
    } else {
        client.sendMessage(username + " sent file: " + fileName);
    }
}

        } catch (Exception e) {
            close();
        }
    }

    private void close() {
        try {
            ChatServer.clients.remove(this);
            socket.close();
        } catch (Exception ignored) {}
    }
}