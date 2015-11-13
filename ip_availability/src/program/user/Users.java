package program.user;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import program.Client;
import program.Server;


public class Users 
{
	private Map<String, User> users;
	
	public Users()
	{
		users = Collections.synchronizedMap(new HashMap<String, User>());
	}

	public void StartSession(String name, Client client, Server server) throws IOException 
	{
		if(!users.containsKey(name))
		{
			users.put(name, new User());
		}

		users.get(name).StartSession(client, server);
	}
	
	public void EndSession(String name)
	{
		if(SessionExist(name))
		{
			users.get(name).EndSession();
		}
	}
	
	public boolean UserLoggedIn(String name) 
	{
		return SessionExist(name);
	}
	
	public int GetTotalActiveSession(String name) 
	{
		return users.get(name).NumberActiveSessions();
	}

	public boolean SessionExist(String name)
	{
		return users.containsKey(name) && users.get(name).UserLoggedIn();
	}
	
	public List<String> GetUnactiveUsers()
	{
		List<String> result = (List<String>) users.keySet()
												.stream()
												.filter(p -> !users.get(p).UserLoggedIn())
												.collect(Collectors.toList());
	
		return result;
	}
	
	public List<String> GetActiveUsers()
	{
		List<String> result = new LinkedList<String>();
		
		result.addAll(users.keySet());
		
		return result;
	}
	
	public User GetUserInfo(String userName)
	{
		if(!users.containsKey(userName))
		{
			return null;
		}
		
		return users.get(userName);
	}
	
	public String UserSessionActivity(String user, String separator)
	{
		User info = GetUserInfo(user);
		
		if(info != null)
		{
			return info.UserLoginSessions(separator);
		}	
		
		return null;
	}
}