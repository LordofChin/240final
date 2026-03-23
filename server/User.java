import java.net.InetAddress;
import java.util.ArrayList;

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