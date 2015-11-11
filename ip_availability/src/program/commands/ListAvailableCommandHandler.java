package program.commands;

import program.Users;

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
		if(users.UserLoggedIn(args[0]))
		{
			String[] activeUsersNames = users.GetActiveUsers();
			String userString = "";
			
			for(String curretUser : activeUsersNames)
			{
				userString += ":";
				userString += curretUser;
			}

			callback.OnResultEvent("ok" + userString);
			
			return true;
		}
		
		return false;
	}
}