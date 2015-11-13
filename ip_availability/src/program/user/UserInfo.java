package program.user;

import java.io.IOException;

import program.Client;
import program.Server;

public class UserInfo 
{
	private boolean activeSession;
	private int numberActiveSessions;
	private Client client;
	
	public UserInfo()
	{
		numberActiveSessions = 0;
	}
	
	public boolean UserLoggedIn()
	{
		return activeSession;
	}
	
	public void StartSession(Client clientArg, Server server) throws IOException
	{
		numberActiveSessions++;
		System.out.println("start session");
		if(client != null && client != clientArg)
		{
			System.out.println("end previous client");
			server.OnSessionEnd(client);
		}
		
		client = clientArg;
		activeSession = true;
	}
	
	public void EndSession()
	{
		activeSession = false;
	}

	public int NumberActiveSessions() 
	{
		return numberActiveSessions;
	}
}