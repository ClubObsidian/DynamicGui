package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import com.clubobsidian.dynamicgui.gui.Gui2;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.objects.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.objects.ModeEnum;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.util.ChatColor;
import com.clubobsidian.dynamicgui.util.FunctionUtil;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

public class GuiManager {

	private static GuiManager instance;
	
	private List<Gui2> guis;
	private Map<UUID, Gui2> playerGuis;
	
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
		for(Gui2 gui : this.guis)
		{
			if(gui.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public Gui2 getGuiByName(String name)
	{
		for(Gui2 gui : this.guis)
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
	
	public List<Gui2> getGuis()
	{
		return this.guis;
	}
	
	public boolean hasGuiCurrently(PlayerWrapper<?> playerWrapper)
	{
		return this.playerGuis.get(playerWrapper.getUniqueId()) != null;
	}
	
	public void cleanupGui(PlayerWrapper<?> playerWrapper)
	{
		this.playerGuis.remove(playerWrapper.getUniqueId());
	}

	public Gui2 getCurrentGui(PlayerWrapper<?> playerWrapper)
	{
		return this.playerGuis.get(playerWrapper.getUniqueId());
	}
	
	public boolean openGui(PlayerWrapper<?> playerWrapper, String guiName)
	{
		return this.openGui(playerWrapper, this.getGuiByName(guiName));
	}
	
	public boolean openGui(PlayerWrapper<?> playerWrapper, Gui2 gui)
	{
		if(gui == null)
		{
			playerWrapper.sendMessage(DynamicGUI.get().getNoGui());
			return false;
		}
		
		Gui2 clonedGUI = gui.clone();
		boolean ran = FunctionUtil.tryFunctions(clonedGUI, playerWrapper);
		
		InventoryWrapper<?> inventoryWrapper = clonedGUI.buildInventory(playerWrapper);
		
		if(inventoryWrapper == null)
			return false;
		
		if(ran)
		{
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
		}
		return ran;
	}
	
	private void loadGuis()
	{
		this.loadFileGuis();
		this.loadRemoteGuis();
	}
	
	private void loadFileGuis()
	{
		File guiFolder = DynamicGUI.get().getPlugin().getGuiFolder();
		
		Collection<File> ar = FileUtils.listFiles(guiFolder, new String[]{"yml", "yaml", "json", "hocon"}, true);
		
		if(ar.size() != 0)
		{
			for(File file : ar)
			{
				try
				{
					Configuration yaml = Configuration.load(file);
					String guiName = file.getName().substring(0, file.getName().lastIndexOf("."));
					this.loadGuiFromConfiguration(guiName, yaml);
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
	
	public void loadRemoteGuis()
	{
		File configFile = new File(DynamicGUI.get().getPlugin().getDataFolder(), "config.yml");
		File tempDirectory = new File(DynamicGUI.get().getPlugin().getDataFolder(), "temp");
		if(tempDirectory.exists())
		{
			try 
			{
				FileUtils.deleteDirectory(tempDirectory);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		tempDirectory.mkdir();
		
		Configuration config = Configuration.load(configFile);
		if(config.get("remote-guis") != null)
		{
			ConfigurationSection remote = config.getConfigurationSection("remote-guis");
			for(String key :  remote.getKeys())
			{
				ConfigurationSection guiSection = remote.getConfigurationSection(key);
				String strUrl = guiSection.getString("url");
				try 
				{
					URL url = new URL(strUrl);
					String guiName = guiSection.getString("file-name");
					File backupFile = new File(DynamicGUI.get().getPlugin().getGuiFolder(), guiName);
					File tempFile = new File(tempDirectory, guiName);
					Configuration guiConfiguration = Configuration.load(url, tempFile, backupFile);
					this.loadGuiFromConfiguration(guiName, guiConfiguration);
				} 
				catch (MalformedURLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private void loadGuiFromConfiguration(String guiName, Configuration config)
	{
		String guiTitle = config.getString("gui-title");
		int rows = config.getInt("rows");
		List<Slot> slots = this.createSlots(rows, config);
		
		final Gui2 gui = this.createGui(config, DynamicGUI.get().getPlugin(), guiName, guiTitle, rows, slots);

		this.guis.add(gui);
		DynamicGUI.get().getLogger().info("gui " + gui.getName() + " has been loaded!");
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
				int amount = 1;
				if(section.get("amount") != null)
				{
					amount = section.getInt("amount");
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

				
				
				slots.add(new Slot(icon, name, nbt, data, close, lore, enchants, i, functions, failFunctions, leftClickFunctions, leftClickFailFunctions, rightClickFunctions, rightClickFailFunctions, middleClickFunctions, middleClickFailFunctions, loadFunctions, failLoadFunctions, amount));
			}
		}
		
		return slots;
	}
	
	private Gui2 createGui(final Configuration yaml, final DynamicGUIPlugin plugin, final String guiName, final  String guiTitle, final int rows, final List<Slot> slots)
	{
		//int commandsLoaded = 0;
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
		
		List<Function> functions = this.createFunctions(yaml, "functions");
		Map<String,List<Function>> failFunctions = this.createFailFunctions(yaml, "-failfunctions");
		
		return new Gui2(guiName, guiTitle, rows, close, modeEnum, npcIds, slots, locations, functions, failFunctions);
	}
}