package com.chat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    // 🔥 shared list of all connected clients
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(5000);

            System.out.println("Server started...");
            System.out.println("Waiting for clients...");

            while (true) {

                Socket socket = server.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                // create handler
                ClientHandler handler = new ClientHandler(socket);

                // 🔥 add client to list
                clients.add(handler);

                // start thread
                new Thread(handler).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}