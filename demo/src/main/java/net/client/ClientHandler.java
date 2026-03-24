package net.client;

import java.net.DatagramPacket;
import net.core.PacketHandler;

public class ClientHandler implements PacketHandler {
    @Override
    public void handle(DatagramPacket packet) {
        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("\n[Server]: " + message);
    }
}