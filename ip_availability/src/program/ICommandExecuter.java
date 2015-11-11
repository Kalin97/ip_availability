package program;

public interface ICommandExecuter 
{
	public boolean execute(String input);
	public boolean isActive();
	public boolean hasOutput();
	public String getOutput();
}
