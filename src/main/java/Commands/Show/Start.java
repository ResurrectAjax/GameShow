package Commands.Show;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class Start extends ChildCommand{

	private Main main;
	public Start(Main main) {
		this.main = main;
	}
	
	@Override
	public void perform(Player player, String[] args) {
		
		
		
	}

	@Override
	public String[] getArguments(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "start";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show start <word>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Start a game with a given word";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
