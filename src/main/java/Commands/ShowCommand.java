package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Commands.Show.Admin.Reload;
import Commands.Show.Host.Reset;
import Commands.Show.Host.Start;
import Commands.Show.Host.UserCommand;
import Commands.Show.Host.Win;
import Commands.Show.User.CloseGui;
import Commands.Show.User.Guess;
import Interfaces.ParentCommand;
import Main.Main;

public class ShowCommand extends ParentCommand{
	private List<ParentCommand> subcommands;
	
	public ShowCommand(Main main) {
		subcommands = new ArrayList<ParentCommand>(Arrays.asList(
				new UserCommand(main),
				new Start(main),
				new Help.HelpCommand(main),
				new Win(main),
				new Reset(main),
				new Guess(main),
				new CloseGui(),
				new Reload(main)
				));
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
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
