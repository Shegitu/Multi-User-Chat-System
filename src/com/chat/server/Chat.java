package com.chat.server;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat {
    public static void main(String[] args) throws Exception{
        ServerSocket server= new ServerSocket(5000);
System.out.println("waiting...");
Socket socket = server.accept();
System.out.println("Client connected");
server.close();
    }
}
