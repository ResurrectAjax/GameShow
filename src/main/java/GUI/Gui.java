package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import General.GeneralMethods;
import Main.Main;

/**
 * Class for creating a GUI
 * 
 * @author ResurrectAjax
 * */
public class Gui {
	private Inventory inventory;
	private FileConfiguration guiConfig;
	private Player player;
	private Main main;
	
	private InventoryType type;
	
	private int page, totalPages;
	
	/**
	 * Create a GUI
	 * @param main instance of {@link Main.Main}
	 * @param player player who opens GUI
	 * @param size row size of GUI
	 * @param name name of the GUI
	 * @param replaceStr String to replace something with
	 * */
	public Gui(Main main, Player player, int size, String name, @Nullable String replaceStr) {
		this.main = main;
		this.player = player;
		this.guiConfig = main.getGuiConfig();
		this.type = InventoryType.CHEST;
		this.inventory = Bukkit.createInventory(new CustomInventoryHolder(), size*9, GeneralMethods.format(name, replaceStr));
		
		createTemplate(name, replaceStr);
		
	}
	
	public InventoryType getType() {
		return type;
	}
	
	/**
	 * Set a slot 
	 * */
	public void setSlot(ItemStack item, int slot) {
		this.inventory.setItem(slot, item);
	}
	
	/**
	 * Set the inventory of gui
	 * @param inventory inventory to
	 * */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Get the gui
	 * @return gui's inventory
	 * */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Open the gui
	 * */
	public void openInventory() {
		player.openInventory(inventory);
	}
	
	/**
	 * Create the template with replace string
	 * @param guiName name of the gui
	 * @param replaceStr String to replace something with
	 * */
	public void createTemplate(String guiName, @Nullable String replaceStr) {
		templateLayout(Gui.getGuiSection(guiName)[0] + "." + Gui.getGuiSection(guiName)[1], replaceStr);
	}
	
	/**
	 * Create the gui and bind it to player
	 * @param guiName name of the gui
	 * @param replaceStr string to replace something with
	 * */
	private void templateLayout(String guiName, @Nullable String replaceStr) {
		
		Inventory inventory = getInventory();
		ItemStack backgroundItem = new ItemStack(Material.valueOf(guiConfig.getString(main.getName() + "." + guiName + ".BackgroundItem")));
		
		for(int i = 0; i < inventory.getSize(); i++) {
			setSlot(backgroundItem, i);
		}
		
		for(String key : guiConfig.getConfigurationSection(main.getName() + "." + guiName + ".Items").getKeys(false)) {
			int ID = guiConfig.getInt(main.getName() + "." + guiName + ".Items." + key + ".ID");
			ItemStack item = new ItemStack(Material.valueOf(guiConfig.getString(main.getName() + "." + guiName + ".Items." + key + ".Material")), 1, (byte) ID);
			List<String> lore = new ArrayList<String>();
			String name = GeneralMethods.format(guiConfig.getString(main.getName() + "." + guiName + ".Items." + key + ".Name"));
			ItemMeta meta = item.getItemMeta();
			int slot = guiConfig.getInt(main.getName() + "." + guiName + ".Items." + key + ".ItemSlot");
			
			for(String lores : guiConfig.getStringList(main.getName() + "." + guiName + ".Items." + key + ".Lore")) {
				lore.add(GeneralMethods.format(lores, replaceStr));
			}
			
			meta.setDisplayName(GeneralMethods.format(name, replaceStr));
			
			meta.setLore(lore);
			
			item.setItemMeta(meta);
			
			inventory.setItem(slot, item);
		}
		
		main.getGuiManager().setCurrentGui(player.getUniqueId(), this);
	}
	
	/**
	 * Create an item list with pages
	 * @param startIndex inventory slot where the list will begin
	 * @param width amount of list columns (max 9)
	 * @param height amount of list rows (max 6)
	 * @param inventory inventory to put list in
	 * @param items list of items to put in gui
	 * @param page index of the current page
	 * */
	public void createItemList(int startIndex, int width, int height, List<ItemStack> items, int page) {
		int totalPages = 1;
		
		int listIndex = 0;
		for(int i = startIndex; i < (startIndex+width); i++) {
			this.inventory.setItem(i, items.get(listIndex));
			listIndex++;
		}
		
		this.page = page;
		this.totalPages = totalPages;
		
		main.getGuiManager().setCurrentGui(player.getUniqueId(), this);
	}
	
	/**
	 * Get the total amount of list pages
	 * @return int totalPages
	 * */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * Get the current page index
	 * @return int pageIndex
	 * */
	public int getPage() {
		return page;
	}

	/**
	 * Get the list of items in the gui.yml section
	 * @return list of ItemStacks
	 * */
	public static List<ItemStack> getGuiItems(String[] sectionGui) {
		Main mn = Main.getInstance();
		FileConfiguration guiConf = mn.getGuiConfig();
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		String pluginName = Main.getInstance().getName();
		if(guiConf.contains(pluginName + "." + sectionGui[0] + "." + sectionGui[1] + ".Items")) {
			for(String key : guiConf.getConfigurationSection(pluginName + "." + sectionGui[0] + "." + sectionGui[1] + ".Items").getKeys(false)) {
				ItemStack item = new ItemStack(Material.valueOf(guiConf.getString(pluginName + "." + sectionGui[0] + "." + sectionGui[1] + ".Items." + key + ".Material")));
				List<String> lore = new ArrayList<String>();
				String name = GeneralMethods.format(guiConf.getString(pluginName + "." + sectionGui[0] + "." + sectionGui[1] + ".Items." + key + ".Name"));
				ItemMeta meta = item.getItemMeta();
				
				for(String lores : guiConf.getStringList(pluginName + "." + sectionGui[0] + "." + sectionGui[1] + ".Items." + key + ".Lore")) {
					lore.add(GeneralMethods.format(lores));
				}
				
				meta.setDisplayName(name);
				
				meta.setLore(lore);
				item.setItemMeta(meta);
				
				items.add(item);
			}		
		}
		
		return items;
	}
	
	/**
	 * Get the configuration sections of a GUI name
	 * @param displayName name of the GUI
	 * @return String[2] with String[0] being the section it's in and String[1] being the GUI it's in
	 * */
	public static String[] getGuiSection(String displayName) {
		String[] sectionGui = new String[2];
		
		Main mn = Main.getInstance();
		FileConfiguration guiConf = mn.getGuiConfig();
		
		String pluginName = Main.getInstance().getName();
		for(String section : guiConf.getConfigurationSection(pluginName).getKeys(false)) {
			for(String gui : guiConf.getConfigurationSection(pluginName + "." + section).getKeys(false)) {
				String display = guiConf.getString(pluginName + "." + section + "." + gui + ".GUIName");
				if(GeneralMethods.format(display).equals(GeneralMethods.format(displayName))) {
					sectionGui[0] = section;
					sectionGui[1] = gui;
				}	
			}
		}
		return sectionGui;
	}
	
	/**
	 * Get the item from a 'Items' section in the gui.yml file
	 * @param guiSections String[2] with String[0] being the section it's in and String[1] being the GUI it's in
	 * @param displayName the item's name
	 * */
	public static String getItemInSection(String[] guiSections, String displayName) {
		String guiSection = Main.getInstance().getName() + "." + guiSections[0] + "." + guiSections[1] + ".Items";
		
		Main mn = Main.getInstance();
		FileConfiguration guiConf = mn.getGuiConfig();
		for(String item : guiConf.getConfigurationSection(guiSection).getKeys(false)) {
			String name = guiConf.getString(guiSection + "." + item + ".Name");
			if(GeneralMethods.format(name).equals(displayName)) {
				return item;
			}
		}
		return null;
	}
}
