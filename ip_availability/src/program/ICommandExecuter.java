package program;

public interface ICommandExecuter 
{
	public boolean execute(String command);
	public boolean isActive();
	public boolean hasOutput();
	public String getOutput();
}
