package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import Commands.Show.Host.Show;
import Commands.Show.Host.ShowRepository;
import Main.Main;

public class QuitListener implements Listener{
	
	private Main main;
	public QuitListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		ShowRepository showRepo = main.getShowRepository();
		if(showRepo.getShowByHost(player.getUniqueId()) != null) {
			Show show = showRepo.getShowByHost(player.getUniqueId());
			showRepo.endShow(show);
		}
		if(showRepo.getShowByUser(player.getUniqueId()) != null) {
			Show show = showRepo.getShowByUser(player.getUniqueId());
			show.removeUser(player.getUniqueId());
		}
	}
	
}
