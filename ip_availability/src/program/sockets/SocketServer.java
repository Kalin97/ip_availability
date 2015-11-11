package program.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import program.ClientSession;
import program.ICommandExecuter;
import program.Server;
import program.input.DataIO;
import program.input.SocketIO;

public class SocketServer implements Server
{
	private ServerSocket serverSocket;
	private boolean isServerRunning;
	private ICommandExecuter commandExecuter;
	
	
	public SocketServer(int port, ICommandExecuter commandExecuter) throws IOException
	{
		isServerRunning = true;
		serverSocket = new ServerSocket(port);
		
		this.commandExecuter = commandExecuter;
	}
	
	public void startServer() throws IOException
	{
		while(serverRunning())
		{
			Socket client = waitForClient();
			
			startSession(client);
		}
	}

	private Socket waitForClient() throws IOException
	{
		return serverSocket.accept();
	}

	private void startSession(Socket client) throws IOException
	{
		DataIO stream = new SocketIO(client);
		
		ClientSession clientSession = new ClientSession(this, stream, commandExecuter);
		clientSession.run();
	}
	
	private boolean serverRunning() 
	{
		return isServerRunning;
	}

	public void stopServer() throws IOException
	{
		isServerRunning = false;
		
		serverSocket.close();
	}
}
