package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Commands.Show.Start;
import Commands.Show.UserCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class ShowCommand extends ParentCommand{
	private List<ParentCommand> subcommands;
	
	public ShowCommand(Main main) {
		subcommands = new ArrayList<ParentCommand>(Arrays.asList(
				new UserCommand(main),
				new Start(main),
				new Help.HelpCommand(main)
				));
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return "show.host";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "show";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show <subcommand>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return subcommands;
	}

	@Override
	public boolean hasGUI() {
		// TODO Auto-generated method stub
		return false;
	}

}
