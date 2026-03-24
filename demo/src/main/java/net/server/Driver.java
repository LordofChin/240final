package net.server;

import java.net.*;
import net.core.*;

public class Driver 
{
	public static void main(String[] args) 
	{
			int port = 3478;
			DatagramSocket socket = null;
            try {
                socket = new DatagramSocket(port);
            } catch (SocketException e) {
                System.err.println("Error initializing server socket: " + e.getMessage());
            }

			DatagramReceiver receiver = new DatagramReceiver(socket, new ServerHandler(socket));
			receiver.start();

    }
}


