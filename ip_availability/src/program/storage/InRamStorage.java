package program.storage;

import program.user.Users;

public class InRamStorage implements IStorage
{
	private final Users users;
	
	public InRamStorage()
	{
		users = new Users();
	}
	
	@Override
	public Users GetUsers() {
		return users;
	}
}
