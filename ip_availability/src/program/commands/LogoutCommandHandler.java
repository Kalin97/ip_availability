package program.commands;

import program.user.Users;

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
		callback.OnResultEvent("ok");
			
		return true;
	}

}