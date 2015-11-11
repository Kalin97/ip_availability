package program.commands;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import program.ICommandExecuter;
import program.Users;

public class LogActivityCommandExecuter implements ICommandExecuter, OnResultCommandEvent
{
	private boolean isActive;
	private boolean hasOutput;
	private String output;
	
	final private Users users;
	
	public LogActivityCommandExecuter()
	{
		isActive = true;
		hasOutput = false;
		users = new Users();
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
	public boolean execute(String command) 
	{
		ICommandHandler handler = SetHandler(command);
		handler.execute(GetParams(command));
		
		return true;
	}
	
	private ICommandHandler SetHandler(String input)
	{
		String command = input.split(":")[1];
		
		switch(command)
		{
		case "login": 
			return new LoginCommandHandler(this, users);
		case "logout":
			return new LogoutCommandHandler(this, users);
		case "info":
			return new InfoCommandHandler(this, users);
		case "listavailable":
			return new ListAvailableCommandHandler(this, users);
		case "shutdown":
			Callable<Void> predicate = new Callable<Void>()
			{ 
				public Void call() 
				{ 
					changeActiveState(false);
					return null;
				} 
			};
			return new ShutDownCommandHandler(predicate);
		}
		
		OnResultEvent("error:unknowncommand");
		
		return null;
	}
	
	private String[] GetParams(String input) {
		
		String[] parts = input.split(":");
		
		List<String> params = new LinkedList<String>(Arrays.asList(parts));
		params.remove(1); 
		
		return params.toArray(new String[params.size()]);
	}
}
