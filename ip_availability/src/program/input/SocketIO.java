package program.input;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketIO implements DataIO
{
	final Socket stream;
	final PrintStream out;
	final Scanner in;
	
	public SocketIO(Socket stream) throws IOException
	{
		this.stream = stream;
		
		out = new PrintStream(stream.getOutputStream());
		in  = new Scanner(stream.getInputStream());
	}
	
	@Override
	public String input() 
	{
		return in.next();
	}

	@Override
	public void output(String output) 
	{
		out.println(output);
	}

	@Override
	public void close() throws IOException 
	{
		in.close();
		out.close();
		stream.close();
	}
}
