package program.commands;

import program.Users;

public class InfoCommandHandler implements ICommandHandler 
{
	OnResultCommandEvent callback;
	Users  users;
	
	public InfoCommandHandler(OnResultCommandEvent callback, Users users) 
	{
		this.callback = callback;
		this.users  = users;
	}

	@Override
	public boolean execute(String[] args) 
	{
		if(users.UserLoggedIn(args[0]))
		{
			String result = "";
			result += "ok" + ":";
			result += args[1] + ":";
			result += users.UserLoggedIn(args[1]) + ":";
			result += "" + users.GetTotalActiveSession(args[1]);
			
			callback.OnResultEvent(result);
			
			return true;
		}

		return false;
	}
}