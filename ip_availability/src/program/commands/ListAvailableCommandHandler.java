package program.commands;

import java.util.List;

import program.user.Users;


public class ListAvailableCommandHandler implements ICommandHandler 
{
	OnResultCommandEvent callback;
	Users  users;
	
	public ListAvailableCommandHandler(OnResultCommandEvent callback, Users users) 
	{
		this.callback = callback;
		this.users  = users;
	}

	@Override
	public boolean execute(String[] args) 
	{
		List<String> activeUsersNames = users.GetActiveUsers();
		String userString = "";
		
		for(String curretUser : activeUsersNames)
		{
			userString += ":";
			userString += curretUser;
		}

		callback.OnResultEvent("ok" + userString);
		
		return true;
	}
}