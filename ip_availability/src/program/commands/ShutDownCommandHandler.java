package program.commands;

import java.util.concurrent.Callable;

public class ShutDownCommandHandler implements ICommandHandler 
{
	Callable<?> callback;
	
	public ShutDownCommandHandler(Callable<?> callback)
	{
		this.callback = callback;
	}
	
	@Override
	public boolean execute(String[] args) {

		try {
			callback.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}