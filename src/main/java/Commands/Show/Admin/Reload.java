package Commands.Show.Admin;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class Reload extends ChildCommand{

	private Main main;
	public Reload(Main main) {
		this.main = main;
	}
	
	@Override
	public void perform(Player player, String[] args) {
		main.reload();
		player.sendMessage(GeneralMethods.getReloadMessage());
		
	}

	@Override
	public String[] getArguments(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return "show.admin";
	}

	@Override
	public boolean hasGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "reload";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show reload";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Reloads the configuration files";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
