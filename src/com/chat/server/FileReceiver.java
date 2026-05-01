package com.chat.server;

import java.io.*;
import java.net.Socket;

public class FileReceiver implements Runnable {

    private Socket socket;

    public FileReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // 1. Read file name
            String fileName = dis.readUTF();

            // 2. Read file size
            long fileSize = dis.readLong();

            // 3. Create output file
            FileOutputStream fos = new FileOutputStream("received_" + fileName);

            byte[] buffer = new byte[4096];
            int read;
            long remaining = fileSize;

            while ((read = dis.read(buffer, 0, (int)Math.min(buffer.length, remaining))) > 0) {
                fos.write(buffer, 0, read);
                remaining -= read;
            }

            fos.close();

            System.out.println("File received: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}