package program.commands;

import java.io.IOException;

import program.Server;

public class ShutDownCommandHandler implements ICommandHandler 
{
	Server server;
	
	public ShutDownCommandHandler(Server server)
	{
		this.server = server;
	}
	
	@Override
	public boolean execute(String[] args) 
	{
		try 
		{
			server.stopServer();
		} 
		catch (IOException e) 
		{}
	
		return true;
	}
}