import java.net.*;
import java.util.HashMap;
import java.util.ArrayList;

public class GameServer 
{
	public static void main(String[] args) {
	    	int port = 3478;
		try (DatagramSocket socket = new DatagramSocket(port)) 
		{
			System.out.println("Server started on port " + port + ". Waiting for messages or moves...");

			byte[] buffer = new byte[1024]; // 1KB buffer
					    
			Users users = new Users();

			while (true) 
			{
                		DatagramPacket rpacket = new DatagramPacket(buffer, buffer.length);
                		socket.receive(rpacket); // Blocks until a message arrives
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
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class GameBoard 
{
	private char[][] grid;
}
public class Users
{
	HashMap<InetAddress, User> ip2user;
	
	public Users()
	{
		ip2user = new HashMap<>();
	}

	public void add(InetAddress ip, User usr)
	{
		ip2user.put(ip,usr);
	}
	public boolean check(InetAddress ip)
	{
		return ip2user.containsKey(ip);
	}
	public boolean needsCreating(InetAddress ip)
	{
		return ip2user.get(ip).needsCreating();
	}

	public User getUser(InetAddress ip)
	{
		return ip2user.get(ip);
	}

	public HashMap<InetAddress, User> getIP2User()
	{
		return ip2user;
	}
}

public class User
{
	private boolean instantiated = false;
	private boolean created = false;

	private String username;
	private ArrayList<InetAddress> known_ips;

	User(InetAddress ip){
		this.known_ips = new ArrayList<>();
		this.known_ips.add(ip);
		this.instantiated = true;
		
		
		this.username = null;
	}
	public void editUsername(String username)
	{
		this.username = username;
		this.created = true;
	}

	public boolean needsCreating()
	{
		return !(created && instantiated);
	}
	public String getUsername()
	{
		return this.username;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (InetAddress ip: known_ips)
		{
			sb.append(String.format("\t%s", ip.toString()));
		}
		return String.format("Username: %s\nIPs:\n%s\ninstantiated?: %b\ncreated?: %b\n", username, sb.toString(), instantiated, created);
	}

}

