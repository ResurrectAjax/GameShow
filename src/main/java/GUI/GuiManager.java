package GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Commands.Show.Host.Show;
import Commands.Show.Host.ShowRepository;
import General.GeneralMethods;
import Main.Main;

public class GuiManager {
	private Main main;
	private FileConfiguration guiConfig;
	private List<String> MHF_Heads = new ArrayList<String>();
	private HashMap<UUID, Integer> selectedRaid = new HashMap<UUID, Integer>();
	private HashMap<UUID, Gui> currentGui = new HashMap<UUID, Gui>();
	
	public Gui getCurrentGui(UUID uuid) {
		return currentGui.get(uuid);
	}
	
	public void setCurrentGui(UUID uuid, Gui gui) {
		currentGui.put(uuid, gui);
	}

	public GuiManager(Main main) {
		this.main = main;
		
		MHF_Heads.addAll(Arrays.asList(
				"Back",
				"Next"
				));
		
	}
	
	public List<String> getMHFList() {
		return MHF_Heads;
	}
	
	public HashMap<UUID, Integer> getSelectedRaid() {
		return selectedRaid;
	}
	
	public Gui confirmGui(Player player, ItemStack item) {
		guiConfig = main.getGuiConfig();
		Gui gui = new Gui(main, player, guiConfig.getInt("Raid.RaidParty.Confirm.Rows"), GeneralMethods.format(guiConfig.getString("Raid.RaidParty.Confirm.GUIName")), null);
		Inventory inventory = gui.getInventory();
		
		inventory.setItem(4, item);
		gui.openInventory();
		
		return gui;
	}
	
	
	public Gui chooseGui(Player player) {
		guiConfig = main.getGuiConfig();
		ShowRepository showRepo = main.getShowRepository();
		Show show = showRepo.getShowByUser(player.getUniqueId());
		Gui gui = new Gui(main, player, guiConfig.getInt("GameShow.Choose.ChooseGUI.Rows"), GeneralMethods.format(guiConfig.getString("GameShow.Choose.ChooseGUI.GUIName")), null);
		
		String word = show.getWord();
		
		int centerWord = word.length()/2;
		int total = 4-centerWord;
		
		Map<String, ItemStack> loadedHeads = main.getFileManager().getLoadedHeads();
		List<ItemStack> letters = new ArrayList<ItemStack>();
		
		String[] charact = show.getGivenLetters().get(player.getUniqueId());
		for(int i = 0; i < show.getGivenLetters().get(player.getUniqueId()).length; i++) {
			if(charact[i] == null) letters.add(loadedHeads.get("-"));
			else letters.add(loadedHeads.get(charact[i].toUpperCase()));
		}
		gui.createItemList(total, word.length(), 1, letters, 0);
		
		gui.openInventory();
		
		return gui;
	}
	

}
