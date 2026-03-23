
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DatagramSender {
    private static DatagramSender instance;
    private static DatagramSocket socket;


    private DatagramSender() {
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            System.err.println("Error initializing DatagramSender: " + e.getMessage());
        }
    }

    public static DatagramSender getInstance() {
        if (instance == null) {
            instance = new DatagramSender();
        }
        return instance;
    }

    public static void send(InetAddress ip, int port, String message) {
        if (instance == null) {
            System.err.println("DatagramSender instance is not initialized.");
            return;
        }
        try {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
            socket.send(packet);
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
