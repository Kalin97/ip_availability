package program;

import java.io.IOException;

import program.sockets.SocketServer;
import program.storage.IStorage;
import program.storage.InRamStorage;

public class Main 
{
	public static void main(String[] args)
	{
		IStorage storage = new InRamStorage();
		
		try 
		{
			final Server socketServer = new SocketServer(27015, storage);
			
			socketServer.startServer();
			socketServer.stopServer();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}