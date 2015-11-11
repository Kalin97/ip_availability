package program;

import java.io.IOException;
import program.input.DataIO;

public class ClientSession
{
	DataIO stream;
	Server server;
	ICommandExecuter commandExecuter;
	
	public ClientSession(Server server, DataIO stream, ICommandExecuter commandExecuter)
	{
		this.stream = stream; 
		this.server = server;
		this.commandExecuter = commandExecuter;
	}
	
	public void run() throws IOException
	{
		do
		{
			String command = stream.input();
			commandExecuter.execute(command);
			
			if(commandExecuter.hasOutput())
			{
				stream.output(commandExecuter.getOutput());
			}
			
		} while(commandExecuter.isActive());
		
		close();
	}
	
	public void close() throws IOException
	{
		stream.close();
	}	
}