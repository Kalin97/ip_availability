package program.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
			Socket client = waitForClient();
			
			startSession(client);
		}
		
		stopServer();
	}

	private Socket waitForClient() throws IOException
	{
		return serverSocket.accept();
	}

	private void startSession(Socket client) throws IOException
	{
		DataIO stream = new SocketIO(client);
		ICommandExecuter commandExecuter = new LogActivityCommandExecuter(data);
		ClientSession clientSession = new ClientSession(this, stream, commandExecuter);
		
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
			endSession(session);
		}
		
		serverSocket.close();
	}
	
	public synchronized void OnSessionEnd(ClientSession session) throws IOException
	{
		endSession(session);
	}
	
	private synchronized void endSession(ClientSession session) throws IOException
	{
		session.close();
		activeSessions.remove(session);
	}
}
