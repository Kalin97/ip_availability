package program.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import program.Client;
import program.ICommandExecuter;
import program.Server;
import program.storage.IStorage;
import program.user.Users;

public class LogActivityCommandExecuter implements ICommandExecuter, OnResultCommandEvent
{
	private Map<String, ICommandHandler> commands;
	private boolean isActive;
	private boolean hasOutput;
	private Users users;
	private String output;
	private Server server;
	private Client client;
	private String currentUser;
	
	public LogActivityCommandExecuter(IStorage data, Server server, Client client)
	{
		currentUser = null;
		this.client = client;
		this.server = server;
		users = data.GetUsers();
		initCommands();
		isActive = true;
		hasOutput = false;
	}
	
	private void initCommands()
	{
		commands = new HashMap<String, ICommandHandler>();
		commands.put("login", new LoginCommandHandler(this, users, client, server));
		commands.put("logout", new LogoutCommandHandler(this, users, client, server));
		commands.put("info", new InfoCommandHandler(this, users));
		commands.put("listavailable", new ListAvailableCommandHandler(this, users));
		commands.put("listabsent", new ListabsentCommandHandler(this, users));
		
		Callable<Void> predicate = new Callable<Void>()
		{ 
			public Void call() throws IOException 
			{ 
				server.stopServer();
				return null;
			} 
		};
		commands.put("shutdown", new ShutDownCommandHandler(predicate));
	}
	
	public void changeActiveState(boolean state)
	{
		isActive = state;
	}
	
	@Override
	public boolean isActive() 
	{
		return isActive;
	}

	@Override
	public boolean hasOutput() 
	{
		return hasOutput;
	}

	@Override
	public String getOutput() 
	{
		hasOutput = false;
		return output;
	}
	
	public void OnResultEvent(String result)
	{
		hasOutput = true;
		output = result;
	}
	
	@Override
	public boolean execute(String input) 
	{
		String command = ParseCommand(input);
		ICommandHandler handler = getCommand(command);
		
		if(!Validation(command, handler))
		{
			return false;
		}

		handler.execute(getParams(input, 0));

		return true;
	}

	private boolean Validation(String command, ICommandHandler handler) 
	{
		if(handler == null)
		{
			OnResultEvent("error:unknowncommand");
			
			return false;
		}
		
		if(!IsUserLoggedIn() && !command.equals("login"))
		{
			OnResultEvent("error:notlogged");
			
			return false;
		}
		
		return true;
	}
	
	private String ParseCommand(String input)
	{
		return input.split(":")[0];
	}
	
	private ICommandHandler getCommand(String command)
	{
		if(commands.containsKey(command))
		{
			return commands.get(command);
		}
		
		return null;
	}
	
	private String[] getParams(String input, int indexToRemove) 
	{
		String[] parts = input.split(":");
		
		List<String> params = new LinkedList<String>(Arrays.asList(parts));
		params.remove(indexToRemove); 
		
		return params.toArray(new String[params.size()]);
	}

	private boolean IsUserLoggedIn()
	{
		return currentUser != null;
	}
	
	@Override
	public void OnLogin(String user) 
	{
		currentUser = user;
	}

	@Override
	public void OnLogout() 
	{
		if(users.UserLoggedIn(currentUser))
		{
			users.EndSession(currentUser);
		}
	}

	@Override
	public void Close() 
	{
		OnLogout();
	}
}
