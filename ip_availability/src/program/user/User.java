package program.user;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import program.Client;
import program.Server;

public class User 
{
	private boolean activeSession;
	private int numberActiveSessions;
	private Client client;
	private List<Interval> userLoginSessions;
	private Interval currentTimeInterval;
	
	public User()
	{
		numberActiveSessions = 0;
		userLoginSessions = new LinkedList<Interval>();
	}
	
	public boolean UserLoggedIn()
	{
		return activeSession;
	}
	
	public void StartSession(Client clientArg, Server server) throws IOException
	{
		currentTimeInterval = new Interval(new Date());
		
		numberActiveSessions++;
		if(client != null && client != clientArg)
		{
			server.OnSessionEnd(client);
		}
		
		client = clientArg;
		activeSession = true;
	}
	
	public void EndSession()
	{
		currentTimeInterval.SetTo(new Date());
		userLoginSessions.add(currentTimeInterval);
		activeSession = false;
		client = null;
	}

	public String UserLoginSessions(String separator)
	{
		String result = "";
		
		for(Interval interval : userLoginSessions)
		{
			result += separator;
			result += interval.FormatedFromTo();
		}
		
		return result;
	}
	
	public int NumberActiveSessions() 
	{
		return numberActiveSessions;
	}
}