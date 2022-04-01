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

public class AddUser extends ChildCommand{

	private Main main;
	public AddUser(Main main) {
		this.main = main;
	}
	
	@Override
	public void perform(Player player, String[] args) {
		ShowRepository showRepo = main.getShowRepository();
		FileConfiguration lang = main.getLanguage();
		
		if(args.length < 3) player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
		else if(Bukkit.getPlayer(args[2]) == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.NotExist.Message"), "%Player%", args[2]));
		else {
			Player user = Bukkit.getPlayer(args[2]);
			
			Show show = showRepo.getShowByHost(player.getUniqueId());
			if(show != null) {
				if(showRepo.isInShow(user.getUniqueId())) {
					player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.AlreadyInShow.Message"), "%Player%", user.getName()));
					return;
				}
				else if(showRepo.getShowByUser(player.getUniqueId()) != null) {
					player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.AlreadyInShow.Message")));
					return;
				}
				else show.addUser(user.getUniqueId());
			}
			else if(player.getUniqueId().equals(Bukkit.getPlayer(args[2]).getUniqueId())) {
				player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.SelfInvite.Message"), "%Player%", user.getName()));
				return;
			}
			else showRepo.createShow(player.getUniqueId(), user.getUniqueId());
			
			show = showRepo.getShowByHost(player.getUniqueId());
			String message = GeneralMethods.format(lang.getString("Command.Show.User.Added.Host.Message"), "%Player%", user.getName());
			player.sendMessage(message);
			
			for(UUID use : show.getUsers()) {
				if(use.equals(user.getUniqueId())) continue;
				if(Bukkit.getPlayer(use) == null) continue;
				Bukkit.getPlayer(use).sendMessage(message);
			}
			
			message = GeneralMethods.format(lang.getString("Command.Show.User.Added.User.Message"), "%Player%", player.getName());
			user.sendMessage(message);
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
		return "add";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show user add <user>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Add a user to the game";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
