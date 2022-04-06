package Commands.Show.Host;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import General.GeneralMethods;
import Main.Main;

public class ShowRepository {
	private List<Show> shows = new ArrayList<Show>();
	
	private Main main;
	public ShowRepository(Main main) {
		this.main = main;
	}
	
	public void createShow(UUID host, UUID user) {
		shows.add(new Show(host, user));
	}
	
	public Show getShowByHost(UUID host) {
		for(Show show : shows) {
			if(show.getHost().equals(host)) return show;
		}
		return null;
	}
	
	public boolean isInShow(UUID uuid) {
		for(Show show : shows) {
			if(show.getHost().equals(uuid) || show.getUsers().contains(uuid)) return true;
		}
		return false;
	}
	
	public Show getShowByUser(UUID user) {
		for(Show show : shows) {
			if(show.getUsers().contains(user)) return show;
		}
		return null;
	}
	
	public void endShow(Show show) {
		shows.remove(show);
		
		FileConfiguration lang = main.getLanguage();
		
		if(show.getWord() == null) return;
		String message = GeneralMethods.format(lang.getString("Command.Show.WonGame.Word.Message"), "%Word%", show.getWord());
		
		if(Bukkit.getPlayer(show.getHost()) != null) Bukkit.getPlayer(show.getHost()).sendMessage(message);
		for(UUID user : show.getUsers()) {
			if(Bukkit.getPlayer(user) == null) continue;
			Bukkit.getPlayer(user).sendMessage(message);
		}
	}
}
