package program.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import program.Client;
import program.ClientSession;
import program.ICommandExecuter;
import program.Server;
import program.commands.LogActivityCommandExecuter;
import program.input.DataIO;
import program.input.SocketIO;
import program.storage.IStorage;

public class SocketServer implements Server
{
	private ServerSocket serverSocket;
	private boolean isServerRunning;
	private IStorage data;
	private Set<ClientSession> activeSessions;
	
	
	public SocketServer(int port, IStorage data) throws IOException
	{
		isServerRunning = true;
		serverSocket = new ServerSocket(port);
		activeSessions = Collections.synchronizedSet(new HashSet<ClientSession>());
		this.data = data;
	}
	
	public void startServer() throws IOException
	{
		while(serverRunning())
		{
			Socket client = null;
			try 
			{
				client = waitForClient();
				startSession(client);
			} 
			catch (Exception e) 
			{}
		}
	}

	private Socket waitForClient() throws IOException
	{
		return serverSocket.accept();
	}

	private void startSession(Socket client) throws IOException
	{
		DataIO stream = new SocketIO(client);
		ClientSession clientSession = new ClientSession(this, stream);
		ICommandExecuter commandExecuter = new LogActivityCommandExecuter(data, this, clientSession);
		clientSession.SetCommandExecuter(commandExecuter);
		
		activeSessions.add(clientSession);
		new Thread(clientSession).start();
	}
	
	private synchronized boolean serverRunning() 
	{
		return isServerRunning;
	}

	public synchronized void stopServer() throws IOException
	{
		isServerRunning = false;
		
		for(ClientSession session : activeSessions)
		{
			EndSession(session);
		}
		
		serverSocket.close();
	}
	
	public void OnSessionEnd(Client session) throws IOException
	{
		EndSession(session);
	}
	
	private synchronized void EndSession(Client session) throws IOException
	{
		session.close();
		activeSessions.remove(session);
	}
}
