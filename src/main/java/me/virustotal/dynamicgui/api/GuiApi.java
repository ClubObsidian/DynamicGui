package me.virustotal.dynamicgui.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.configuration.Configuration;
import me.virustotal.dynamicgui.configuration.ConfigurationSection;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.EmptyFunction;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.objects.CLocation;
import me.virustotal.dynamicgui.objects.ModeEnum;
import me.virustotal.dynamicgui.objects.EnchantmentWrapper;
import me.virustotal.dynamicgui.objects.SoundWrapper;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.util.ChatColor;

import org.apache.commons.io.FileUtils;

public class GuiApi {

	private static List<GUI> guis = new ArrayList<GUI>();
	private static Map<UUID, GUI> playerGuis = new HashMap<UUID, GUI>();
	private static Map<UUID, GUI> temporaryGuiMap = new HashMap<UUID, GUI>(); //Maps player uuids and temporary guis.
	
	public static boolean hasGuiTitle(String title)
	{
		for(GUI gui : guis)
		{
			if(gui.getTitle().equals(title))
				return true;
		}
		return false;
	}
	
	public static GUI getGuiByTitle(String title)
	{
		for(GUI gui : guis)
		{
			if(gui.getTitle().equals(title))
			{	
				return gui.clone();
			}
		}
		return null;
	}
	
	public static boolean hasGuiName(String name)
	{
		for(GUI gui : guis)
		{
			if(gui.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public static GUI getGuiByName(String name)
	{
		for(GUI gui : guis)
		{
			if(gui.getName().equals(name))
			{
				return gui.clone();
			}
		}
		return null;
	}
	
	public static GUI getTemporaryGuiForPlayer(PlayerWrapper<?> playerWrapper)
	{
		return GuiApi.getTemporaryGuiForPlayer(playerWrapper.getUniqueId());
	}
	
	public static GUI getTemporaryGuiForPlayer(UUID uuid)
	{
		if(!(GuiApi.temporaryGuiMap.containsKey(uuid)))
			return null;
		
		return GuiApi.temporaryGuiMap.get(uuid);
	}
	
	public static void openTemporaryGui(GUI gui, PlayerWrapper<?> player)
	{
		gui.buildInventory(player);//TODO
		GuiApi.temporaryGuiMap.put(player.getUniqueId(), gui);
	}
	
	public static void openTemporaryGui(GUI gui, UUID uuid)
	{
		GuiApi.openTemporaryGui(gui, DynamicGUI.getInstance().getServer().getPlayer(uuid));
	}
	
	public static void loadGuis()
	{
		DynamicGUIPlugin<?, ?> plugin = DynamicGUI.getInstance().getPlugin();
		File guiFolder = plugin.getGuiFolder();
		//File[] ar = guiFolder.listFiles();
		
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
						List<Slot> slots = GuiApi.getSlots(rows, yaml);
						//System.out.println("slot size: " + slots.size());
						
						final GUI gui = new GUI(GuiApi.getGui(yaml, plugin, guiName, guiTitle, rows, slots));

						guis.add(gui);
						plugin.getLogger().log(Level.INFO, "gui " + gui.getName() + " has been loaded!");
					}
				}	
				catch(NullPointerException ex)
				{
					plugin.getLogger().log(Level.INFO, "Error loading in file: " + file.getName());
					ex.printStackTrace();
				}	
			}
		} 
		else 
		{
			plugin.getLogger().log(Level.SEVERE, "No guis found, please add guis or issues may be encountered!");
		}
	}

	public static void reloadGuis()
	{
		DynamicGUI.getInstance().getPlugin().getLogger().log(Level.INFO, "Force reloading guis!");
		guis.clear();
		loadGuis();
	}
	
	public static List<GUI> getGuis()
	{
		return GuiApi.guis;
	}
	
	
	private static List<Slot> getSlots(int rows, Configuration yaml)
	{
		ArrayList<Slot> slots = new ArrayList<Slot>();
		for(int i = 0; i < rows * 9; i++)
		{
			
			if(yaml.get("" + i) == null)
			{
				slots.add(null);
			} 
			else 
			{
				ConfigurationSection section = yaml.getConfigurationSection(i + "");
				String icon = section.getString("icon");
				String name = null;

				if(section.get("name") != null)
					name = ChatColor.translateAlternateColorCodes('&', section.getString("name"));

				String nbt = null;
				if(section.get("nbt") != null)
					nbt = section.getString("nbt");
				
				List<Function> functions = GuiApi.getFunctions(section, "functions");
				List<Function> leftClickFunctions = GuiApi.getFunctions(section, "leftclick-functions");
				List<Function> rightClickFunctions = GuiApi.getFunctions(section, "rightclick-functions");
				List<Function> middleClickFunctions = GuiApi.getFunctions(section, "middleclick-functions");
				
				
				Map<String,List<Function>> failFunctions = GuiApi.getFailFunctions(section, "-failfunctions");
				Map<String,List<Function>> leftClickFailFunctions = GuiApi.getFailFunctions(section, "-leftclickfailfunctions");
				Map<String,List<Function>> rightClickFailFunctions = GuiApi.getFailFunctions(section, "-rightclickfailfunctions");
				Map<String,List<Function>> middleClickFailFunctions = GuiApi.getFailFunctions(section, "-middleclickfailfunctions");
				//fail functions
				
			
				
				List<Function> loadFunctions = new ArrayList<Function>();
				if(section.get("loadfunctions") != null)
				{
					for(String string : section.getStringList("loadfunctions"))
					{
						String[] array = FunctionApi.parseData(string);
						if(FunctionApi.getFunctionByName(array[0]) == null)
							DynamicGUI.getInstance().getPlugin().getLogger().log(Level.SEVERE, "A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");
						
						Function func = new EmptyFunction(array[0], array[1]);
						loadFunctions.add(func);
					}
				}
				
				Map<String, List<Function>> failLoadFunctions = new HashMap<String, List<Function>>();
				for(String key : section.getKeys())
				{
					if(key.endsWith("-failloadfunctions"))
					{
						ArrayList<Function> failFuncs = new ArrayList<Function>();
						for(String string : section.getStringList(key))
						{
							String[] array = FunctionApi.parseData(string);
							if(FunctionApi.getFunctionByName(array[0]) == null)
								DynamicGUI.getInstance().getPlugin().getLogger().log(Level.SEVERE, "A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");
							
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
	
	private static GUI getGui(final Configuration yaml, final DynamicGUIPlugin<?,?> plugin, final String guiName,final  String guiTitle,final int rows, final List<Slot> slots)
	{
		//int commandsLoaded = 0;
		String permission = null;
		if(yaml.get("permission") != null)
			permission = yaml.getString("permission");

		String pMessage = null;
		if(yaml.get("pmessage") != null)
			pMessage = ChatColor.translateAlternateColorCodes('&', yaml.getString("pmessage"));

		if(yaml.get("alias") != null)
		{
			for(String al : yaml.getStringList("alias"))
			{
				plugin.loadCommand(guiName, al);
			}
			//commandsLoaded++;
		}

		Boolean close = null;
		if(yaml.get("close") != null)
			close = yaml.getBoolean("close");


		List<CLocation> locs = null; 
		if(yaml.get("locations") != null)
		{
			locs = new ArrayList<CLocation>();
			for(String loc : yaml.getStringList("locations"))
			{
				locs.add(new CLocation(loc));
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
		
		return new GUI(guiName, guiTitle, rows, permission,pMessage, close, modeEnum, npcIds, slots, locs, openingSounds);
	}

	private static ArrayList<Function> getFunctions(ConfigurationSection section, String name)
	{
		ArrayList<Function> functions = new ArrayList<Function>();
		if(section.get(name) != null)
		{
			for(String string : section.getStringList(name))
			{
				String[] array = FunctionApi.parseData(string);
				if(FunctionApi.getFunctionByName(array[0]) == null)
					DynamicGUI.getInstance().getPlugin().getLogger().log(Level.SEVERE, "A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");

				Function func = new EmptyFunction(array[0], array[1]);
				functions.add(func);

			}
		}
		return functions;
	}
	
	private static Map<String,List<Function>> getFailFunctions(ConfigurationSection section, String end)
	{
		Map<String, List<Function>> failFunctions = new HashMap<String, List<Function>>(); //check ends with
		for(String key : section.getKeys())
		{
			if(key.endsWith(end))
			{
				List<Function> failFuncs = new ArrayList<Function>();
				for(String string : section.getStringList(key))
				{
					String[] array = FunctionApi.parseData(string);
					if(FunctionApi.getFunctionByName(array[0]) == null)
						DynamicGUI.getInstance().getPlugin().getLogger().log(Level.SEVERE, "A function cannot be found by the name " + array[0] + " is a dependency not yet loaded?");
					
					Function func = new EmptyFunction(array[0], array[1]);
					failFuncs.add(func);
				}
				String[] split = key.split("-");
				String str = split[0];
				if(split.length > 3) //was 2
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
	
	public static boolean hasGUICurrently(PlayerWrapper<?> playerWrapper)
	{
		return GuiApi.playerGuis.keySet().contains(playerWrapper.getUniqueId());
	}
	
	public static void cleanupGUI(PlayerWrapper<?> playerWrapper)
	{
		GuiApi.playerGuis.remove(playerWrapper.getUniqueId());
		GuiApi.temporaryGuiMap.remove(playerWrapper.getUniqueId());
	}

	public static GUI getCurrentGUI(PlayerWrapper<?> player) 
	{
		return GuiApi.playerGuis.get(player.getUniqueId());
	}
}
