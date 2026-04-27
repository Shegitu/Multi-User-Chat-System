package com.chat.client;
import java.net.Socket;
public class ChatClient{
    public static void main(String[] args)throws Exception{
        Socket socket = new Socket ("localhost", 5000);
        System.out.println("Connected to server");
        socket.close();
    }
    
}
