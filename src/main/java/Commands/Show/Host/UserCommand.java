package Commands.Show.Host;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Commands.Show.Host.User.AddUser;
import Commands.Show.Host.User.RemoveUser;
import Interfaces.ParentCommand;
import Main.Main;

public class UserCommand extends ParentCommand{
	
	private List<ParentCommand> subcommands;
	
	public UserCommand(Main main) {
		subcommands = new ArrayList<ParentCommand>(Arrays.asList(
				new AddUser(main),
				new RemoveUser(),
				new Help.HelpCommand(main)
				));
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return "show.host";
	}

	@Override
	public boolean hasGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "user";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show user help";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Get a list of all the user commands";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return subcommands;
	}

}
