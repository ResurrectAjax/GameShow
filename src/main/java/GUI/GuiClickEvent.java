package GUI;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import Commands.Show.Host.Show;
import Commands.Show.Host.ShowRepository;
import Commands.Show.User.CloseGui;
import Commands.Show.User.Guess;
import General.GeneralMethods;
import Main.Main;

public class GuiClickEvent implements Listener{
	private Main main;
	private GuiManager guiManager;
	private HashMap<UUID, UUID> confirmUUID = new HashMap<UUID, UUID>();
	private HashMap<UUID, Integer> containerID = new HashMap<UUID, Integer>();
	public GuiClickEvent(Main main) {
		this.main = main;
		guiManager = main.getGuiManager();
	}
	
	@EventHandler
	public void onGuiClick(InventoryClickEvent event) {
		FileConfiguration guiConfig = main.getGuiConfig();
		Player player = (Player)event.getWhoClicked();
		
		if(event.getInventory().getHolder() instanceof CustomInventoryHolder) {
			event.setCancelled(true);
			
			String title = event.getView().getTitle();
			ItemStack currentItem = event.getCurrentItem();
			
			if(currentItem != null) {
				
				if(currentItem.getType().equals(Material.SKULL_ITEM)) {
					if(guiManager.getMHFList().contains(ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()))) {
						int page = guiManager.getCurrentGui(player.getUniqueId()).getPage(), totalPages = guiManager.getCurrentGui(player.getUniqueId()).getTotalPages()-1;
						
						if(currentItem.equals(GeneralMethods.getPlayerHead("MHF_ArrowLeft")) && page > 0) page--;
						else if(currentItem.equals(GeneralMethods.getPlayerHead("MHF_ArrowRight")) && page < totalPages) page++;
						
						//Open GUI on new page
					}
				}
				else {
					String configSection = "";
					if(Gui.getGuiItems(Gui.getGuiSection(title)) != null && Gui.getGuiItems(Gui.getGuiSection(title)).stream().anyMatch(it -> it.getItemMeta().equals(currentItem.getItemMeta()))) {
						String itemSection = Gui.getItemInSection(Gui.getGuiSection(title), currentItem.getItemMeta().getDisplayName());
						configSection = main.getName() +  "." + Gui.getGuiSection(title)[0] + "." + Gui.getGuiSection(title)[1] + ".Items." + itemSection;
					}
					
					if(guiConfig.contains(configSection) && configSection != null) {
						runCommandIfExists(player, configSection);	
						containerID.remove(player.getUniqueId());
					}	
					
				}
			}
		}
	}
	
	private void runCommandIfExists(Player player, String configSection) {
		FileConfiguration guiConfig = main.getGuiConfig();
		
		if(guiConfig.getConfigurationSection(configSection).contains("RunCommand")) {
			player.closeInventory();
			
			String player2 = "";
			if(confirmUUID.containsKey(player.getUniqueId())) {
				player2 = Bukkit.getOfflinePlayer(confirmUUID.get(player.getUniqueId())).getName();
				confirmUUID.remove(player.getUniqueId());
			}
			player.performCommand(GeneralMethods.format(guiConfig.getString(configSection + ".RunCommand"), player2));
		}	
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(!(event.getPlayer() instanceof Player)) return;
		if(!(event.getInventory().getHolder() instanceof CustomInventoryHolder)) return;
		Player player = (Player) event.getPlayer();
		
		ShowRepository showRepo = main.getShowRepository();
		Show show = showRepo.getShowByUser(player.getUniqueId());
		
		if(show == null) return;
		Bukkit.getScheduler().runTaskLater(main, () -> {
			if(!show.getGuessingUsers().contains(player.getUniqueId())) CloseGui.skipGuess(player);
			else Guess.guessWord(player);
		}, 1L);
	}
}
