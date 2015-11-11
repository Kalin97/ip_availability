package program.commands;

import program.Users;
import program.input.DataIO;

public class ListAvailableCommandHandler implements CommandHandler 
{
	DataIO stream;
	Users  users;
	
	public ListAvailableCommandHandler(DataIO stream, Users users) 
	{
		this.stream = stream;
		this.users  = users;
	}

	@Override
	public boolean execute(String[] args) 
	{
		if(users.UserLoggedIn(args[0]))
		{
			String[] activeUsersNames = users.GetActiveUsers();
			String userString = "";
			
			for(String curretUser : activeUsersNames)
			{
				userString += ":";
				userString += curretUser;
			}

			stream.output("ok" + userString);
			
			return true;
		}
		
		return false;
	}
}