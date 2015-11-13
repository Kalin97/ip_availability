package program.commands;

import java.io.IOException;

import program.Client;
import program.Server;
import program.user.Users;

public class LoginCommandHandler implements ICommandHandler 
{
	private LogActivityCommandExecuter executer;
	private Users users;
	private Client client;
	private Server server;
	
	public LoginCommandHandler(LogActivityCommandExecuter executer, Users users, Client client, Server server)
	{
		this.executer = executer;
		this.server = server;
		this.client = client;
		this.users  = users;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		String user = GetUser(args);
		
		if(user == null)
		{
			return false;
		}
		
		Login(user);

		executer.OnResultEvent("ok");
		
		return true;
	}

	private void Login(String user) 
	{
		if(user.equals(executer.CurrentUser()))
		{
			return;
		}
		
		executer.OnLogout();
		
		try 
		{
			users.StartSession(user, client, server);
		} 
		catch (IOException e) 
		{}
		
		executer.OnLogin(user);
	}

	private String GetUser(String[] args) 
	{
		if(args.length == 0)
		{
			return null;
		}
		
		return args[0];
	}
}