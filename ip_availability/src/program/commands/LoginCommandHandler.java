package program.commands;

import java.io.IOException;

import program.Client;
import program.Server;
import program.user.Users;

public class LoginCommandHandler implements ICommandHandler 
{
	private OnResultCommandEvent callback;
	private Users users;
	private Client client;
	private Server server;
	
	public LoginCommandHandler(OnResultCommandEvent callback, Users users, Client client, Server server)
	{
		this.server = server;
		this.client = client;
		this.callback = callback;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		if(args.length == 0)
		{
			return false;
		}
		
		try 
		{
			users.StartSession(args[0], client, server);
		} 
		catch (IOException e) 
		{}
		
		callback.OnResultEvent("ok");
		
		return true;
	}
}