package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Commands.Show.Host.Show;
import Commands.Show.Host.ShowRepository;
import Commands.Show.Host.Win;
import Commands.Show.Host.User.RemoveUser;
import Main.Main;

public class ChatListener implements Listener{

	private Main main;
	public ChatListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoin(AsyncPlayerChatEvent event) {
		ShowRepository showRepo = main.getShowRepository();
		
		Player player = event.getPlayer();
		Show show = showRepo.getShowByUser(player.getUniqueId());
		if(show == null) return;
		if(!show.getGuessingUsers().contains(player.getUniqueId())) return;
		
		if(show.getWord().equalsIgnoreCase(event.getMessage())) Win.winGame(show, player);
		else RemoveUser.removePlayer(Bukkit.getPlayer(show.getHost()), player.getName());
		
		event.setCancelled(true);
	}
}
