package program.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import program.ICommandExecuter;
import program.Users;
import program.storage.IStorage;

public class LogActivityCommandExecuter implements ICommandExecuter, OnResultCommandEvent
{
	private Map<String, ICommandHandler> commands;
	private boolean isActive;
	private boolean hasOutput;
	private Users users;
	private String output;
	
	public LogActivityCommandExecuter(IStorage data)
	{
		users = data.GetUsers();
		initCommands();
		isActive = true;
		hasOutput = false;
	}
	
	private void initCommands()
	{
		commands = new HashMap<String, ICommandHandler>();
		commands.put("login", new LoginCommandHandler(this, users));
		commands.put("logout", new LogoutCommandHandler(this, users));
		commands.put("info", new InfoCommandHandler(this, users));
		commands.put("listavailable", new ListAvailableCommandHandler(this, users));

		Callable<Void> predicate = new Callable<Void>()
		{ 
			public Void call() 
			{ 
				changeActiveState(false);
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
		ICommandHandler handler = getCommand(input);
		handler.execute(getParams(input));
		
		return true;
	}
	
	private ICommandHandler getCommand(String input)
	{
		String command = input.split(":")[1];
		
		if(commands.containsKey(command))
		{
			return commands.get(command);
		}

		OnResultEvent("error:unknowncommand");
		
		return null;
	}
	
	private String[] getParams(String input) {
		
		String[] parts = input.split(":");
		
		List<String> params = new LinkedList<String>(Arrays.asList(parts));
		params.remove(1); 
		
		return params.toArray(new String[params.size()]);
	}
}
