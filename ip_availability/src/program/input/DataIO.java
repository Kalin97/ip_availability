package program.input;

import java.io.IOException;

public interface DataIO 
{
	public String input();
	public void output(String out);
	public void close() throws IOException;
	
}