package program;

public class UserInfo 
{
	private boolean activeSession;
	private int numberActiveSessions;
	
	public UserInfo()
	{
		numberActiveSessions = 0;

		StartSession();
	}
	
	public boolean UserLoggedIn()
	{
		return activeSession;
	}
	
	public void StartSession()
	{
		numberActiveSessions++;
		
		activeSession = true;
	}
	
	public void EndSession()
	{
		activeSession = false;
	}

	public int NumberActiveSessions() 
	{
		return numberActiveSessions;
	}
}