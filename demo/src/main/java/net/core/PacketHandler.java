package net.core;
import java.net.DatagramPacket;

public interface PacketHandler {
    void handle(DatagramPacket packet);
}