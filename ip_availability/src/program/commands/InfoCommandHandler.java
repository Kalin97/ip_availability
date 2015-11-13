package program.commands;

import program.user.Users;

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
		String result = "";
		result += "ok" + ":";
		result += args[0] + ":";
		result += users.UserLoggedIn(args[0]) + ":";
		result += "" + users.GetTotalActiveSession(args[0]);
		
		callback.OnResultEvent(result);
		
		return true;
	}
}