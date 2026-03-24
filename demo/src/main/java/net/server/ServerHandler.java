package net.server;

import java.io.IOException;
import java.net.*;
import net.core.PacketHandler;

public class ServerHandler implements PacketHandler {

    private final Users users = Users.getInstance();
    private final DatagramSocket socket;

    public ServerHandler(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void handle(DatagramPacket rpacket) {
        try {
            InetAddress ip = rpacket.getAddress();
            int port = rpacket.getPort();

            if (!users.check(ip)) {
                User newUsr = new User(ip);
                users.add(ip, newUsr);

                byte[] data = "Enter username: ".getBytes();
                DatagramPacket spacket = new DatagramPacket(data, data.length, ip, port);
                socket.send(spacket);

                System.out.println("NEW USER:\n" + newUsr);
                return;
            }

            String message = new String(rpacket.getData(), 0, rpacket.getLength());

            if (users.needsCreating(ip)) {
                User usr = users.getUser(ip);
                usr.editUsername(message);
                System.out.println("USER CREATED:\n" + usr);
                return;
            }

            switch (message) {
                case "w" -> System.out.println("UP");
                case "a" -> System.out.println("LEFT");
                case "s" -> System.out.println("DOWN");
                case "d" -> System.out.println("RIGHT");
                default -> System.out.printf("%s: %s\n",
                        users.getUser(ip).getUsername(), message);
            }

        } catch (IOException e) {
            System.err.println("Server handler error: " + e.getMessage());
        }
    }
}