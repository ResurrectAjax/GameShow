package Commands.Show.Host.User;

import java.util.ArrayList;
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

public class RemoveUser extends ChildCommand{
	
	@Override
	public void perform(Player player, String[] args) {
		if(args.length < 3) player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
		else {
			removePlayer(player, args[2]);
		}
	}
	
	public static void removePlayer(Player player, String name) {
		ShowRepository showRepo = Main.getInstance().getShowRepository();
		FileConfiguration lang = Main.getInstance().getLanguage();
		
		if(Bukkit.getPlayer(name) == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.NotExist.Message"), "%Player%", name));
		else {
			Player user = Bukkit.getPlayer(name);
			
			Show show = showRepo.getShowByHost(player.getUniqueId());
			if(show == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.NotExist.Message")));
			else if(!show.getUsers().contains(user.getUniqueId())) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.NotInShow.Message"), "%Player%", name));
			else {
				show.removeUser(user.getUniqueId());
				String message = GeneralMethods.format(lang.getString("Command.Show.User.Removed.Host.Message"), "%Player%", name);
				player.sendMessage(message);
				
				for(UUID use : show.getUsers()) {
					if(use.equals(user.getUniqueId())) continue;
					if(Bukkit.getPlayer(use) == null) continue;
					Bukkit.getPlayer(use).sendMessage(message);
				}
				
				message = GeneralMethods.format(lang.getString("Command.Show.User.Removed.User.Message"), "%Player%", player.getName());
				user.sendMessage(message);
				
				if(show.getUsers().size() < 2) showRepo.endShow(show);
			}
		}
	}

	@Override
	public String[] getArguments(UUID uuid) {
		List<String> players = new ArrayList<String>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getUniqueId().equals(uuid)) continue;
			players.add(player.getName());
		}
		return players.toArray(new String[players.size()]);
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
		return "remove";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show user remove <user>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Remove a user from the game";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
