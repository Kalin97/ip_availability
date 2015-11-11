package program;

import java.io.IOException;

import program.commands.LogActivityCommandExecuter;
import program.sockets.SocketServer;

public class Main 
{
	public static void main(String[] args)
	{
		ICommandExecuter commands = new LogActivityCommandExecuter();
		
		try 
		{
			final Server socketServer = new SocketServer(27015, commands);
			
			socketServer.startServer();
			socketServer.stopServer();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}