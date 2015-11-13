package program.commands;

import java.io.IOException;

import program.Client;
import program.Server;
import program.user.Users;

public class LogoutCommandHandler implements ICommandHandler 
{
	OnResultCommandEvent callback;
	Users  users;
	Client client;
	Server server;
	
	public LogoutCommandHandler(OnResultCommandEvent callback, Users users, Client client, Server server)
	{
		this.server = server;
		this.client = client;
		this.callback = callback;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		callback.OnLogout();

		try 
		{
			server.OnSessionEnd(client);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}

}