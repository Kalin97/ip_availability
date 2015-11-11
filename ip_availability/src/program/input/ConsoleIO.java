package program.input;

import java.util.Scanner;

public class ConsoleIO implements DataIO 
{
	Scanner in;

	public ConsoleIO()
	{
		in = new Scanner(System.in);
	}
	
	@Override
	public String input() 
	{
		return in.next();
	}

	@Override
	public void output(String out) 
	{
		System.out.println(out);
	}

	@Override
	public void close() {
		in.close();
	}
}