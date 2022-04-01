package Commands.Show.Host;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
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
		FileConfiguration lang = main.getLanguage();
		ShowRepository showRepo = main.getShowRepository();
		Show show = showRepo.getShowByHost(player.getUniqueId());
		
		if(args.length < 2) player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
		else if(!Pattern.matches("[a-zA-Z-]+", args[1])) player.sendMessage(GeneralMethods.format(lang.getString("Command.Error.SpecialCharacters.Message")));
		else if(show == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.NotExist.Message")));
		else if(args[1].length() > 9) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.Start.WordTooLong.Message")));
		else if(!show.start(args[1].toLowerCase())) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.Start.NotEnoughUsers.Message")));
		else {
			String message = GeneralMethods.format(lang.getString("Command.Show.Start.Started.Message"), "%Word%", args[1].toLowerCase());
			player.sendMessage(message);
			
			String encrypted = "";
			for(int i = 0; i < args[1].length(); i++) {
				if(args[1].charAt(i) == '-') encrypted += "-";
				else encrypted += "*";
			}
			String messageP = GeneralMethods.format(lang.getString("Command.Show.Start.Started.Message"), "%Word%", encrypted);
			for(UUID user : show.getUsers()) {
				if(Bukkit.getPlayer(user) == null) continue;
				Player userP = Bukkit.getPlayer(user);
			
				userP.sendMessage(messageP);
			}
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
