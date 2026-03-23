import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class DatagramReceiver extends Thread
{
    private static DatagramSocket socket;
    private static DatagramReceiver instance = null;

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        Users users = Users.getInstance();
        ArrayList<String> messages = new ArrayList<>();
        try {
            socket = new DatagramSocket(3478);
            System.out.println("Server started on port 3478. Waiting for messages or moves...");
        } catch (SocketException e) {
            System.err.println("Socket error: " + e.getMessage());
        }
        while (true) 
        { 
            try {
                DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(rpacket);        // Blocks until a message arrives
                InetAddress ip = rpacket.getAddress();
                if(!users.check(ip)) {
                    User newUsr = new User(ip);
                    users.add(ip, newUsr);
                    byte[] data = "Enter your username and password: ".getBytes(); 
                    DatagramPacket spacket = new DatagramPacket(data, data.length, ip, rpacket.getPort());
                    socket.send(spacket);
                    System.out.println("NEW USER INSTANTIATED:\n" + newUsr);
                    continue;
                }
                
                String message = new String(rpacket.getData(), 0, rpacket.getLength());
                
                if(users.needsCreating(ip)) {
                    System.out.println("User needs to be created");
                    User usr = users.getUser(ip);
                    usr.editUsername(message);
                    System.out.println("USER CREATED:\n" + usr);	
                    continue;
                }
                    
                switch(message)
                {
                    case "w": System.out.println("UP");
                        break;
                    case "a": System.out.println("LEFT");
                        break;
                    case "s": System.out.println("DOWN");
                        break;
                    case "d": System.out.println("RIGHT");
                        break;
                    default: System.out.printf("%s: %s\n", users.getUser(ip).getUsername(), message);
                        break;

                }
                
                // Clear the buffer for the next message
                rpacket.setLength(buffer.length);
            } catch (IOException e) {
                System.err.println("IO error: " + e.getMessage());
            }
        }
        
    }

    private DatagramReceiver(){}

    public static DatagramReceiver getInstance() 
    {
        if (instance == null)
        {
            instance = new DatagramReceiver();
        }
        return instance;
    }

    public static void close()
    {
        if (instance != null)
        {
            socket.close();
            instance = null;
        }
    }
}
