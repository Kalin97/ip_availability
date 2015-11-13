package program.commands;

public interface OnResultCommandEvent 
{
	public void OnResultEvent(String result);
	public void OnLogin(String user);
	public void OnLogout();
}
