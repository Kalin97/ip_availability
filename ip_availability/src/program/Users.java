package program;

import java.util.HashMap;
import java.util.Map;


public class Users 
{
	private Map<String, UserInfo> users;
	
	public Users()
	{
		users = new HashMap<String, UserInfo>();
	}

	public void StartSession(String name) 
	{
		if(users.containsKey(name))
		{
			users.get(name).StartSession();
		}
		else
		{
			users.put(name, new UserInfo());
		}
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
	
	public String[] GetActiveUsers()
	{
		String[] result = new String[users.size()];
		
		int index = 0;
		for(String name : users.keySet())
		{
			result[index++] = name;
		}
		
		return result;
	}
	
}