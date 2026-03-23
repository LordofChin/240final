import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;


public class Users implements Serializable
{
	private HashMap<InetAddress, User> ip2user;
	private static Users instance;

	private Users()
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

	public static Users getInstance()
	{
		if (instance == null)
		{
			// check if file exists
			File f = new File("Users.dat");
			if (f.exists() && !f.isDirectory()) {
				instance = read(f);
				if (instance != null) {
					System.out.println("Users instance read from file.");
				} else {
					System.out.println("Failed to read Users instance from file. Creating new instance.");
					instance = new Users();
				}
			} else {
				instance = new Users();
			}
		}
		return instance;
	}

	public static void close()
	{
		if (instance != null)
		{
			instance.write();
		}
		instance = null;
		System.out.println("Users instance closed.");
	}


	// writeObject and readObject methods for serialization
    public void write() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(String.format("Users.dat")))) {
            oos.writeObject(this);
            System.out.println(String.format("%s written to file.", this));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static Users read(File f) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream((f)))) {
            Users u = (Users) ois.readObject();
            //System.out.println(String.format("%s read from file", c));
            return u;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }


    @Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
		for (InetAddress ip: ip2user.keySet())
		{
			sb.append(ip2user.get(ip).toString()).append("\n");
		}
		return sb.toString();
    }
}