package program;

import java.io.IOException;
import program.input.DataIO;

public class ClientSession implements Client, Runnable
{
	DataIO stream;
	Server server;
	ICommandExecuter commandExecuter;
	
	public ClientSession(Server server, DataIO stream)
	{
		this.stream = stream; 
		this.server = server;
	}

	public void SetCommandExecuter(ICommandExecuter commandExecuter) 
	{
		this.commandExecuter = commandExecuter;
	}	
	
	public void run()
	{
		try 
		{
			HandleInput();
		}
		catch (Exception e) 
		{}
		finally
		{
			stopSession();
		}
	}

	private void HandleInput() 
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
	}
	
	public void stopSession()
	{
		try 
		{
			server.OnSessionEnd(this);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void close() throws IOException
	{
		stream.close();
	}

}
