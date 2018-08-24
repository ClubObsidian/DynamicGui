package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.configuration.Configuration;
import com.clubobsidian.dynamicgui.configuration.ConfigurationSection;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.EmptyFunction;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.objects.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.objects.ModeEnum;
import com.clubobsidian.dynamicgui.objects.SoundWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.util.ChatColor;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

public class GuiManager {

	private static GuiManager instance;
	
	private List<GUI> guis;
	private Map<UUID, GUI> playerGuis;
	
	private GuiManager()
	{
		this.guis = new ArrayList<>();
		this.playerGuis = new HashMap<>();
	}
	
	public static GuiManager get()
	{
		if(instance == null)
		{
			instance = new GuiManager();
			instance.loadGuis();
		}
		return instance;
	}
	
	public boolean hasGuiName(String name)
	{
		for(GUI gui : this.guis)
		{
			if(gui.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public GUI getGuiByName(String name)
	{
		for(GUI gui : this.guis)
		{
			if(gui.getName().equals(name))
			{
				return gui.clone();
			}
		}
		return null;
	}

	public void reloadGuis()
	{
		DynamicGUI.get().getLogger().info("Force reloading guis!");
		this.guis.clear();
		this.loadGuis();
	}
	
	public List<GUI> getGuis()
	{
		return this.guis;
	}
	
	public boolean hasGUICurrently(PlayerWrapper<?> playerWrapper)
	{
		return this.playerGuis.get(playerWrapper.getUniqueId()) != null;
	}
	
	public void cleanupGUI(PlayerWrapper<?> playerWrapper)
	{
		this.playerGuis.remove(playerWrapper.getUniqueId());
	}

	public GUI getCurrentGUI(PlayerWrapper<?> playerWrapper)
	{
		return this.playerGuis.get(playerWrapper.getUniqueId());
	}
	
	public boolean openGUI(PlayerWrapper<?> playerWrapper, String guiName)
	{
		return this.openGUI(playerWrapper, this.getGuiByName(guiName));
	}
	
	public boolean openGUI(PlayerWrapper<?> playerWrapper, GUI gui)
	{
		if(gui == null)
		{
			playerWrapper.sendMessage(DynamicGUI.get().getNoGui());
			return false;
		}
		
		GUI clonedGUI = gui.clone();
		DynamicGUI.get().getLogger().info("Cloned gui: " + clonedGUI);
		boolean permNull = (clonedGUI.getPermission() == null);
		if(!permNull)
		{
			if(!playerWrapper.hasPermission(clonedGUI.getPermission()))
			{
				if(clonedGUI.getpMessage() == null)
				{
					playerWrapper.sendMessage(DynamicGUI.get().getNoPermissionGui());
				}
				else
				{
					playerWrapper.sendMessage(clonedGUI.getpMessage());
				}
				return false;
			}
		}

		for(SoundWrapper wrapper : clonedGUI.getOpeningSounds())
		{
			wrapper.playSoundToPlayer(playerWrapper); 
		}
		
		InventoryWrapper<?> inventoryWrapper = clonedGUI.buildInventory(playerWrapper);
		
		if(inventoryWrapper == null)
			return false;
		
		//DynamicGUI.get().getLogger().info("After putting gui into player guis: " + this.hasGUICurrently(playerWrapper));
		playerWrapper.openInventory(inventoryWrapper);
		this.playerGuis.put(playerWrapper.getUniqueId(), clonedGUI);
		DynamicGUI.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.get().getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				playerWrapper.updateInventory();
			}
		},2L);
		
		return true;
	}
	
	private void loadGuis()
	{
		DynamicGUIPlugin plugin = DynamicGUI.get().getPlugin();
		File guiFolder = plugin.getGuiFolder();
		
		Collection<File> ar = FileUtils.listFiles(guiFolder, new String[]{"yml"}, true);
		
		if(ar.size() != 0)
		{
			for(File file : ar)
			{
				try
				{
					if(file.getName().toLowerCase().endsWith(".yml"))
					{
						Configuration yaml = Configuration.load(file);
						String guiName = file.getName().substring(0, file.getName().indexOf("."));

						String guiTitle = yaml.getString("gui-title");
						int rows = yaml.getInt("rows");
						List<Slot> slots = this.createSlots(rows, yaml);
						//System.out.println("slot size: " + slots.size());
						
						final GUI gui = GuiManager.createGUI(yaml, plugin, guiName, guiTitle, rows, slots);

						this.guis.add(gui);
						DynamicGUI.get().getLogger().info("gui " + gui.getName() + " has been loaded!");
					}
				}	
				catch(NullPointerException ex)
				{
					DynamicGUI.get().getLogger().info("Error loading in file: " + file.getName());
					ex.printStackTrace();
				}	
			}
		} 
		else 
		{
			DynamicGUI.get().getLogger().info("No guis found, please add guis or issues may be encountered!");
		}
	}
	
	private Map<String,List<Function>> createFailFunctions(ConfigurationSection section, String end)
	{
		Map<String, List<Function>> failFunctions = new HashMap<>(); //check ends with
		for(String key : section.getKeys())
		{
			if(key.endsWith(end))
			{
				List<Function> failFuncs = new ArrayList<Function>();
				for(String string : section.getStringList(key))
				{
					String[] array = FunctionManager.get().parseData(string);
					if(FunctionManager.get().getFunctionByName(array[0]) == null)
						DynamicGUI.get().getLogger().error("A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");
					
					Function func = new EmptyFunction(array[0], array[1]);
					failFuncs.add(func);
				}
				String[] split = key.split("-");
				String str = split[0];
				if(split.length > 3)
				{
					for(int j = 1; j < split.length - 1; j++)
					{
						str += "-" + split[j];
					}
				}
				failFunctions.put(str, failFuncs);
			}
		}
		return failFunctions;
	}
	
	private List<Function> createFunctions(ConfigurationSection section, String name)
	{
		List<Function> functions = new ArrayList<>();
		if(section.get(name) != null)
		{
			for(String string : section.getStringList(name))
			{
				String[] array = FunctionManager.get().parseData(string);
				if(FunctionManager.get().getFunctionByName(array[0]) == null)
					DynamicGUI.get().getLogger().error("A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");

				Function func = new EmptyFunction(array[0], array[1]);
				functions.add(func);

			}
		}
		return functions;
	}
	
	private List<Slot> createSlots(int rows, Configuration yaml)
	{
		ArrayList<Slot> slots = new ArrayList<Slot>();
		for(int i = 0; i < rows * 9; i++)
		{
			System.out.println("Parsing slot " + i + " value is " + (yaml.get("" + i)));
			if(yaml.get("" + i) != null)
			{
				System.out.println("Parsed slot is not null: " + i);
				ConfigurationSection section = yaml.getConfigurationSection(i + "");
				String icon = section.getString("icon");
				String name = null;

				if(section.get("name") != null)
					name = ChatColor.translateAlternateColorCodes('&', section.getString("name"));

				String nbt = null;
				if(section.get("nbt") != null)
					nbt = section.getString("nbt");
				
				List<Function> functions = this.createFunctions(section, "functions");
				List<Function> leftClickFunctions = this.createFunctions(section, "leftclick-functions");
				List<Function> rightClickFunctions = this.createFunctions(section, "rightclick-functions");
				List<Function> middleClickFunctions = this.createFunctions(section, "middleclick-functions");
				
				
				Map<String,List<Function>> failFunctions = this.createFailFunctions(section, "-failfunctions");
				Map<String,List<Function>> leftClickFailFunctions = this.createFailFunctions(section, "-leftclickfailfunctions");
				Map<String,List<Function>> rightClickFailFunctions = this.createFailFunctions(section, "-rightclickfailfunctions");
				Map<String,List<Function>> middleClickFailFunctions = this.createFailFunctions(section, "-middleclickfailfunctions");
				//fail functions
				
			
				
				List<Function> loadFunctions = new ArrayList<>();
				if(section.get("loadfunctions") != null)
				{
					for(String string : section.getStringList("loadfunctions"))
					{
						String[] array = FunctionManager.get().parseData(string);
						if(FunctionManager.get().getFunctionByName(array[0]) == null)
							DynamicGUI.get().getLogger().error("A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");
						
						Function func = new EmptyFunction(array[0], array[1]);
						loadFunctions.add(func);
					}
				}
				
				Map<String, List<Function>> failLoadFunctions = new HashMap<>();
				for(String key : section.getKeys())
				{
					if(key.endsWith("-failloadfunctions"))
					{
						ArrayList<Function> failFuncs = new ArrayList<Function>();
						for(String string : section.getStringList(key))
						{
							String[] array = FunctionManager.get().parseData(string);
							if(FunctionManager.get().getFunctionByName(array[0]) == null)
								DynamicGUI.get().getLogger().error("A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");
							
							Function func = new EmptyFunction(array[0], array[1]);
							failFuncs.add(func);
						}
						String[] split = key.split("-");
						String str = split[0];
						if(split.length > 2)
						{
							for(int j = 1; j < split.length - 1; j++)
							{
								str += "-" + split[j];
							}
						}
						failLoadFunctions.put(str, failFuncs);
					}
				}
				
				List<String> lore = null;
				if(section.get("lore") != null)
				{
					lore = new ArrayList<String>();
					for(String ls : section.getStringList("lore"))
					{
						lore.add(ChatColor.translateAlternateColorCodes('&', ls));
					}
				}

				List<EnchantmentWrapper> enchants = null;
				if(section.get("enchants") != null)
				{
					enchants = new ArrayList<EnchantmentWrapper>();
					for(String ench : section.getStringList("enchants"))
					{
						String[] args = ench.split(",");
						enchants.add(new EnchantmentWrapper(args[0], Integer.parseInt(args[1])));
					}
				}

				String permission = null;
				if(section.get("permission") != null)
				{
					permission = ChatColor.translateAlternateColorCodes('&',section.getString("permission"));
				}

				int amount = 1;
				if(section.get("amount") != null)
				{
					amount = section.getInt("amount");
				}
				String pMessage = null;
				if(section.get("pmessage") != null)
				{
					pMessage = ChatColor.translateAlternateColorCodes('&',section.getString("pmessage"));
				}

				Boolean close = null;
				if(section.get("close") != null)
				{
					close = section.getBoolean("close");
				}
				
				short data = 0;
				if(section.get("data") != null)
				{
					data = (short) section.getInt("data");
				}

				
				
				slots.add(new Slot(icon, name, nbt, data, close, lore, enchants,permission,pMessage, i, functions, failFunctions, leftClickFunctions, leftClickFailFunctions, rightClickFunctions, rightClickFailFunctions, middleClickFunctions, middleClickFailFunctions, loadFunctions, failLoadFunctions, amount));
			}
		}
		
		return slots;
	}
	
	private static GUI createGUI(final Configuration yaml, final DynamicGUIPlugin plugin, final String guiName, final  String guiTitle, final int rows, final List<Slot> slots)
	{
		//int commandsLoaded = 0;
		String permission = null;
		if(yaml.get("permission") != null)
		{
			permission = yaml.getString("permission");
		}

		String pMessage = null;
		if(yaml.get("pmessage") != null)
		{
			pMessage = ChatColor.translateAlternateColorCodes('&', yaml.getString("pmessage"));
		}

		if(yaml.get("alias") != null)
		{
			for(String alias : yaml.getStringList("alias"))
			{
				plugin.createCommand(guiName, alias);
			}
		}

		Boolean close = null;
		if(yaml.get("close") != null)
		{
			close = yaml.getBoolean("close");
		}


		List<LocationWrapper<?>> locations = new ArrayList<>(); 
		if(yaml.get("locations") != null)
		{
			for(String location : yaml.getStringList("locations"))
			{
				locations.add(LocationManager.get().toLocationWrapper(location));
			}
		}

		ModeEnum modeEnum = ModeEnum.ADD;
		if(yaml.get("mode") != null)
		{
			modeEnum = ModeEnum.valueOf(yaml.getString("mode").toUpperCase());
		}

		List<Integer> npcIds = new ArrayList<>();
		
		if(yaml.get("npc-ids") != null)
		{
			npcIds = yaml.getIntList("npc-ids");
		}
		
		List<SoundWrapper> openingSounds = new ArrayList<>();
		
		if(yaml.get("opening-sounds") != null)
		{
			for(String str : yaml.getStringList("opening-sounds"))
			{
				String[] args = str.split(",");
				openingSounds.add(new SoundWrapper(args[0], Float.parseFloat(args[1]), Float.parseFloat(args[2])));
			}
		}
		
		return new GUI(guiName, guiTitle, rows, permission,pMessage, close, modeEnum, npcIds, slots, locations, openingSounds);
	}
}