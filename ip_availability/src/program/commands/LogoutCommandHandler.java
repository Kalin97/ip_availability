package program.commands;

import program.Users;

public class LogoutCommandHandler implements ICommandHandler 
{
	OnResultCommandEvent callback;
	Users  users;
	
	public LogoutCommandHandler(OnResultCommandEvent callback, Users users)
	{
		this.callback = callback;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		if(users.UserLoggedIn(args[0]))
		{
			users.EndSession(args[0]);
			callback.OnResultEvent("ok");
			
			return true;
		}
		
		return false;
	}

}