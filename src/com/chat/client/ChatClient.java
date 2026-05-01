package com.chat.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    public static void main(String[] args) {

        try {
            // 1. Connect to server
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");

            // 2. Send messages to server
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // 3. Receive messages from server
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // 🔥 THREAD 1: RECEIVE MESSAGES
            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (Exception e) {
                    System.out.println("Disconnected from server");
                }
            });

            receiveThread.start();

            // 🔥 THREAD 2: SEND MESSAGES
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            String message;

           while ((message = input.readLine()) != null) {
    dos.writeUTF(message);
}

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}