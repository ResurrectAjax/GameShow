package Commands.Show.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Commands.Show.Show;
import Commands.Show.ShowRepository;
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
			UUID user = Bukkit.getPlayer(args[2]).getUniqueId();
			
			Show show = showRepo.getShowByHost(player.getUniqueId());
			if(show != null) show.addUser(user);
			else showRepo.createShow(player.getUniqueId(), user);
			
			String message = GeneralMethods.format(lang.getString("Command.Show.User.Added.Message"));
			player.sendMessage(message);
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
		return "Add a user to the show";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
