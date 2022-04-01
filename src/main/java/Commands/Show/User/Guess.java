package Commands.Show.User;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Commands.Show.Host.Show;
import Commands.Show.Host.ShowRepository;
import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class Guess extends ChildCommand{

	private Main main;
	public Guess(Main main) {
		this.main = main;
	}
	
	@Override
	public void perform(Player player, String[] args) {
		ShowRepository showRepo = main.getShowRepository();
		Show show = showRepo.getShowByUser(player.getUniqueId());
		
		if(show == null) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage("/show <subcommand>"));
			return;
		}
		show.addGuessingUser(player.getUniqueId());
		
		player.closeInventory();
	}
	
	public static void guessWord(Player player) {
		FileConfiguration lang = Main.getInstance().getLanguage();
		ShowRepository showRepo = Main.getInstance().getShowRepository();
		Show show = showRepo.getShowByUser(player.getUniqueId());
		
		String message = GeneralMethods.format(lang.getString("Command.Show.Guess.Host.Guessing.Message"), "%Player%", player.getName());
		
		Player host = Bukkit.getPlayer(show.getHost());
		host.sendMessage(message);
		
		
		for(UUID use : show.getUsers()) {
			if(use.equals(player.getUniqueId())) continue;
			if(Bukkit.getPlayer(use) == null) continue;
			Bukkit.getPlayer(use).sendMessage(message);
		}
		
		message = GeneralMethods.format(lang.getString("Command.Show.Guess.User.Guessing.Message"), "%Player%", player.getName());
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
		return "guess";
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
