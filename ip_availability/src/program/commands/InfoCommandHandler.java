package program.commands;

import program.Users;
import program.input.DataIO;

public class InfoCommandHandler implements CommandHandler 
{
	DataIO stream;
	Users  users;
	
	public InfoCommandHandler(DataIO stream, Users users) 
	{
		this.stream = stream;
		this.users  = users;
	}

	@Override
	public boolean execute(String[] args) 
	{
		if(users.UserLoggedIn(args[0]))
		{
			stream.output("ok" + ":");
			stream.output(args[1] + ":");
			stream.output(users.UserLoggedIn(args[1]) + ":");
			stream.output("" + users.GetTotalActiveSession(args[1]));
			
			return true;
		}

		return false;
	}
}