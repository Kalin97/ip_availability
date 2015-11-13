package program.commands;

import java.util.List;

import program.user.Users;


public class ListabsentCommandHandler implements ICommandHandler
{
	private Users users;
	private OnResultCommandEvent callback;
	
	public ListabsentCommandHandler(OnResultCommandEvent callback, Users users) 
	{
		this.users = users;
		this.callback = callback;
	}

	@Override
	public boolean execute(String[] args) 
	{
		List<String> activeUsersNames = users.GetUnactiveUsers();
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
