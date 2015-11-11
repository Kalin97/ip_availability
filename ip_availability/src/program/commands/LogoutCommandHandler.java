package program.commands;

import program.Users;
import program.input.DataIO;

public class LogoutCommandHandler implements CommandHandler 
{
	DataIO stream;
	Users  users;
	
	public LogoutCommandHandler(DataIO stream, Users users)
	{
		this.stream = stream;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		if(users.UserLoggedIn(args[0]))
		{
			users.EndSession(args[0]);
			stream.output("ok");
			
			return true;
		}
		
		return false;
	}

}