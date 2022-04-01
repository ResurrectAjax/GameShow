package Commands.Show.Host;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

public class Win extends ChildCommand{

	private Main main;
	public Win(Main main) {
		this.main = main;
	}
	
	@Override
	public void perform(Player player, String[] args) {
		ShowRepository showRepo = main.getShowRepository();
		FileConfiguration lang = main.getLanguage();
		
		if(args.length < 2) player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
		else if(Bukkit.getPlayer(args[1]) == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.NotExist.Message"), "%Player%", args[1]));
		else {
			Player user = Bukkit.getPlayer(args[1]);
			
			Show show = showRepo.getShowByHost(player.getUniqueId());
			if(show == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.NotExist.Message"), "%Player%", user.getName()));
			else if(!show.getUsers().contains(user.getUniqueId())) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.User.Error.NotInShow.Message"), "%Player%", user.getName()));
			else if(show.getWord() == null) player.sendMessage(GeneralMethods.format(lang.getString("Command.Show.Start.NotStartedYet.Message")));
			else {
				String letter = show.giveRandomLetter(user.getUniqueId());
				if(letter == null) {
					winGame(show, user);
					return;
				}
				
				String message = GeneralMethods.format(lang.getString("Command.Show.Win.ChallengeWon.Host.Message"), "%Player%", user.getName());
				player.sendMessage(GeneralMethods.format(message, "%Letter%", letter));
				
				for(UUID use : show.getUsers()) {
					if(use.equals(user.getUniqueId())) continue;
					if(Bukkit.getPlayer(use) == null) continue;
					Bukkit.getPlayer(use).sendMessage(message);
				}
				
				message = GeneralMethods.format(lang.getString("Command.Show.Win.ChallengeWon.User.Message"), "%Player%", player.getName());
				user.sendMessage(GeneralMethods.format(message, "%Letter%", letter));
				
				main.getGuiManager().chooseGui(user);
			}
		}
	}
	
	public static void winGame(Show show, Player player) {
		FileConfiguration lang = Main.getInstance().getLanguage();
		Player host = Bukkit.getPlayer(show.getHost());
		
		String message1 = GeneralMethods.format(lang.getString("Command.Show.WonGame.Host.Message"), "%Player%", player.getName());
		host.sendMessage(message1);
		
		String message2 = GeneralMethods.format(lang.getString("Command.Show.WonGame.Word.Message"), "%Word%", show.getWord());
		for(UUID use : show.getUsers()) {
			if(use.equals(player.getUniqueId())) continue;
			if(Bukkit.getPlayer(use) == null) continue;
			Bukkit.getPlayer(use).sendMessage(message1);
			Bukkit.getPlayer(use).sendMessage(message2);
		}
		
		message1 = GeneralMethods.format(lang.getString("Command.Show.WonGame.User.Message"), "%Player%", player.getName());
		player.sendMessage(message1);
		player.sendMessage(message2);
		
		show.reset();
	}

	@Override
	public String[] getArguments(UUID uuid) {
		ShowRepository showRepo = main.getShowRepository();
		Show show = showRepo.getShowByHost(uuid);
		if(show == null) return null;
		
		String[] users = new String[show.getUsers().size()];
		for(int i = 0; i < users.length; i++) {
			if(Bukkit.getPlayer(show.getUsers().get(i)) == null) continue;
			users[i] = Bukkit.getPlayer(show.getUsers().get(i)).getName();
		}
		return users;
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
		return "win";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/show win <user>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Appoint a winner and give them a letter";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

}
