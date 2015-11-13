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
	private Map<String, UserInfo> users;
	
	public Users()
	{
		users = Collections.synchronizedMap(new HashMap<String, UserInfo>());
	}

	public void StartSession(String name, Client client, Server server) throws IOException 
	{
		if(!users.containsKey(name))
		{
			users.put(name, new UserInfo());
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
		if(SessionExist(name))
		{
			return users.get(name).NumberActiveSessions();
		}

		return 0;
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
	
	public UserInfo GetUserInfo(String userName)
	{
		if(!users.containsKey(userName))
		{
			return null;
		}
		
		return users.get(userName);
	}
	
}