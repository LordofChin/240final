package net.core;

import java.io.IOException;
import java.net.*;

public class DatagramReceiver extends Thread {
    private final DatagramSocket socket;
    private final PacketHandler handler;

    public DatagramReceiver(DatagramSocket socket, PacketHandler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                handler.handle(packet);

            } catch (IOException e) {
                System.err.println("Receive error: " + e.getMessage());
                break;
            }
        }
    }
}