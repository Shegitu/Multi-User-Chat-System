package com.chat.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e) {
            System.out.println("Error setting up client handler");
            e.printStackTrace();
        }
    }

    // 🔥 method to send message to this client
    public void sendMessage(String message) {
        writer.println(message);
    }

    @Override
    public void run() {

        try {
            String message;

            while ((message = reader.readLine()) != null) {

                System.out.println("Client says: " + message);

                // 🔥 BROADCAST TO ALL CLIENTS
                for (ClientHandler client : ChatServer.clients) {
                    client.sendMessage(message);
                }
            }

        } catch (Exception e) {
            System.out.println("Client disconnected");

        } finally {
            try {
                // 🔥 remove from list when disconnected
                ChatServer.clients.remove(this);

                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}