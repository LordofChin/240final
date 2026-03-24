package net.client;

import java.net.*;
import java.util.Scanner;
import net.core.*;

public class Driver {
    public static void main(String[] args) {
        String serverIP = "3.21.213.171";
        int port = 3478;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress address = InetAddress.getByName(serverIP);

            DatagramReceiver receiver = new DatagramReceiver(socket, new ClientHandler());
            receiver.start();

            System.out.println("Connected to server. Type a message:");

            while (true) {
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) break;

                byte[] data = input.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

                socket.send(packet);
            }

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}