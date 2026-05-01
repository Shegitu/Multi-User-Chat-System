package com.chat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                Socket socket = serverSocket.accept();

                ClientHandler client = new ClientHandler(socket);
                clients.add(client);

                new Thread(client).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}