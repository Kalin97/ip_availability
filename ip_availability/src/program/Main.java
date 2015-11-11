package program;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import program.commands.CommandHandler;
import program.commands.InfoCommandHandler;
import program.commands.ListAvailableCommandHandler;
import program.commands.LoginCommandHandler;
import program.commands.LogoutCommandHandler;
import program.commands.ShutDownCommandHandler;
import program.input.ConsoleIO;
import program.input.DataIO;

public class Main 
{
	private static Users users;
	private static DataIO stream;
	public static boolean closeProgram = false;
	
	public static void main(String[] args)
	{
		stream = new ConsoleIO();
		users  = new Users();
		String input;
		
		while(!closeProgram) 
		{
			input = stream.input();
			CommandHandler handler = SetHandler(input);
			
			String[] params = GetParams(input);
			handler.execute(params);
		}
		
		stream.close();
	}
	
	private static CommandHandler SetHandler(String input)
	{
		String command = input.split(":")[1];
		
		switch(command)
		{
		case "login": 
			return new LoginCommandHandler(stream, users);
		case "logout":
			return new LogoutCommandHandler(stream, users);
		case "info":
			return new InfoCommandHandler(stream, users);
		case "listavailable":
			return new ListAvailableCommandHandler(stream, users);
		case "shutdown":
			Callable<String> predicate = new Callable<String>()
			{ 
				public String call() 
				{ 
					Main.closeProgram = true; 
					return null; 
				} 
			};
			return new ShutDownCommandHandler(predicate);
		}
		
		stream.output("error:unknowncommand");
		return null;
	}
	
	private static String[] GetParams(String input) {
		
		String[] parts = input.split(":");
		
		List<String> params = new LinkedList<String>(Arrays.asList(parts));
		params.remove(1); 
		
		return params.toArray(new String[params.size()]);
	}
}