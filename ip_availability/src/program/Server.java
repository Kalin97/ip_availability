package program;

import java.io.IOException;

public interface Server 
{
	public void startServer() throws IOException;
	public void stopServer()  throws IOException;
	public void OnSessionEnd(Client session) throws IOException;
}
