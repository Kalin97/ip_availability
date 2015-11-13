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
		String user = args[0];
		
		String result = "ok" + ":";
		result += user + ":";
		result += users.UserLoggedIn(user) + ":";
		result += users.GetTotalActiveSession(user);
		result += users.UserSessionActivity(user, ":");
		
		callback.OnResultEvent(result);
		
		return true;
	}
}