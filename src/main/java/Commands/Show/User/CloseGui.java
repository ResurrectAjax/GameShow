package Commands.Show.User;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Commands.Show.Host.Show;
import Commands.Show.Host.ShowRepository;
import GUI.CustomInventoryHolder;
import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class CloseGui extends ChildCommand{
	
	@Override
	public void perform(Player player, String[] args) {
		if(player.getInventory().getHolder() instanceof CustomInventoryHolder) player.closeInventory();
		
	}
	
	public static void skipGuess(Player player) {
		FileConfiguration lang = Main.getInstance().getLanguage();
		ShowRepository showRepo = Main.getInstance().getShowRepository();
		Show show = showRepo.getShowByUser(player.getUniqueId());
		
		if(show == null) return;
		Player host = Bukkit.getPlayer(show.getHost());
		
		String message = GeneralMethods.format(lang.getString("Command.Show.Guess.Host.Skip.Message"), "%Player%", player.getName());
		host.sendMessage(message);
		
		for(UUID use : show.getUsers()) {
			if(use.equals(player.getUniqueId())) continue;
			if(Bukkit.getPlayer(use) == null) continue;
			Bukkit.getPlayer(use).sendMessage(message);
		}
		
		message = GeneralMethods.format(lang.getString("Command.Show.Guess.User.Skip.Message"), "%Player%", player.getName());
		player.sendMessage(message);
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
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "closegui";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
