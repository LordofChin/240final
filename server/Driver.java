package server

public class Driver 
{
	public static void main(String[] args) 
	{
	    	int port = 3478;
			System.out.println("Server started on port " + port + ". Waiting for messages or moves...");
			DatagramReceiver receiver = DatagramReceiver.getInstance();

			Users users = Users.getInstance();

			receiver.start();
			// The main thread can be used for other tasks if needed
			
    }
}


