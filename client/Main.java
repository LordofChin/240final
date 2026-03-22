import java.net.*;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String serverIP = "3.21.213.171";
        int port = 3478;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            
            InetAddress address = InetAddress.getByName(serverIP);
            System.out.println("Connected to server. Type a message and hit Enter:");

            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;

                byte[] data = input.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                
                socket.send(packet);
                System.out.println("Sent!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

