package program.commands;

import program.Users;
import program.input.DataIO;

public class LoginCommandHandler implements CommandHandler 
{
	DataIO stream;
	Users users;
	
	public LoginCommandHandler(DataIO stream, Users users)
	{
		this.stream = stream;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) {
		
		users.StartSession(args[0]);
		stream.output("ok");
		
		return true;
	}
}