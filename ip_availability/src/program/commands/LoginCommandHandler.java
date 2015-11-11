package program.commands;

import program.Users;

public class LoginCommandHandler implements ICommandHandler 
{
	OnResultCommandEvent callback;
	Users users;
	
	public LoginCommandHandler(OnResultCommandEvent callback, Users users)
	{
		this.callback = callback;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		users.StartSession(args[0]);
		callback.OnResultEvent("ok");
		
		return true;
	}
}