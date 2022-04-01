package Commands.Show.Host;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class Reset extends ChildCommand{

	private Main main;
	public Reset(Main main) {
		this.main = main;
	}
	
	@Override
	public void perform(Player player, String[] args) {
		FileConfiguration lang = main.getLanguage();
		ShowRepository showRepo = main.getShowRepository();
		Show show = showRepo.getShowByHost(player.getUniqueId());
		
		if(args.length != 1) player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
		else if(show == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.NotExist.Message")));
		else {
			show.reset();
			player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.Reset.Message")));
		}
	}

	@Override
	public String[] getArguments(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
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
		return "reset";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show reset";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Reset the game";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
