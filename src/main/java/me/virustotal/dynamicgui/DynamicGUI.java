package me.virustotal.dynamicgui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.virustotal.dynamicgui.api.FunctionApi;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.listener.EntityClickListener;
import me.virustotal.dynamicgui.listener.InventoryClickListener;
import me.virustotal.dynamicgui.listener.InventoryCloseListener;
import me.virustotal.dynamicgui.listener.InventoryOpenListener;
import me.virustotal.dynamicgui.listener.PlayerInteractListener;
import me.virustotal.dynamicgui.listener.TemporaryInventoryClickListener;
import me.virustotal.dynamicgui.objects.Replacer;
import me.virustotal.dynamicgui.objects.function.CheckLevelFunction;
import me.virustotal.dynamicgui.objects.function.ConsoleCmdFunction;
import me.virustotal.dynamicgui.objects.function.ExpPayFunction;
import me.virustotal.dynamicgui.objects.function.GuiFunction;
import me.virustotal.dynamicgui.objects.function.NoPermission;
import me.virustotal.dynamicgui.objects.function.ParticleFunction;
import me.virustotal.dynamicgui.objects.function.PayFunction;
import me.virustotal.dynamicgui.objects.function.PermissionFunction;
import me.virustotal.dynamicgui.objects.function.PlayerCmdFunction;
import me.virustotal.dynamicgui.objects.function.PlayerMsgFunction;
import me.virustotal.dynamicgui.objects.function.RemoveSlotFunction;
import me.virustotal.dynamicgui.objects.function.SendFunction;
import me.virustotal.dynamicgui.objects.function.ServerBroadcastFunction;
import me.virustotal.dynamicgui.objects.function.SetDataFunction;
import me.virustotal.dynamicgui.objects.function.SetEnchantsFunction;
import me.virustotal.dynamicgui.objects.function.SetLoreFunction;
import me.virustotal.dynamicgui.objects.function.SetNameFunction;
import me.virustotal.dynamicgui.objects.function.SetTypeFunction;
import me.virustotal.dynamicgui.objects.function.SoundFunction;
import me.virustotal.dynamicgui.objects.function.StatisticFunction;
import me.virustotal.dynamicgui.objects.replacers.OnlinePlayersReplacer;
import me.virustotal.dynamicgui.objects.replacers.PlayerLevelReplacer;
import me.virustotal.dynamicgui.objects.replacers.PlayerReplacer;
import me.virustotal.dynamicgui.objects.replacers.UUIDReplacer;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

public class DynamicGUI<T,U>  {

	public static final String TAG = "DynamicGuiSlot";

	private static DynamicGUI<?,?> instance = null;
	
	private DynamicGUIPlugin<T,U> plugin;
	private EventManager eventManager;
	
	public DynamicGUI(DynamicGUIPlugin<T,U> plugin)
	{
		this.plugin = plugin;
		this.eventManager = new JavaAssistEventManager();
		this.registerListeners();
		//Constructor - Detect sponge or spigot
	}
	
	private void registerListeners() 
	{
		this.eventManager.registerEvents(new me.virustotal.dynamicgui.listener.EntityClickListener<T,U>(this.getPlugin()));
		
	}

	public File configFile;
	public File guiFolder;
	private List<String> registeredCommands = new ArrayList<String>();
	
	private String noPermissionFunction;
	private String noPermissionGui;
	private String noMoney;
	private String noExp;
	private String noPoints;
	private String noGui;
	
	private String version;
	
	private CommandMap cm = null;
	
	private boolean bungeecord;
	private boolean redis;
	
	private static boolean placeHolderAPI = false;
	
	public List<String> invPlayers = new ArrayList<String>();
	public Map<String, GUI> playerGuis = new HashMap<String, GUI>();
	
	
	private Map<String, Integer> serverPlayerCount = new HashMap<String, Integer>();
	
	@Override
	public void onEnable()
	{
		plugin = this;
		this.configFile = new File(this.getDataFolder().getPath(), "config.yml");
		this.guiFolder = new File(this.getDataFolder().getPath(), "guis");

		if(!this.configFile.exists())
			this.saveDefaultConfig();
		else
			this.reloadConfig();

		if(!this.guiFolder.exists())
		{
			this.guiFolder.mkdir();
			this.saveFile("test.yml", new File(this.guiFolder.getPath() + File.separator + "test.yml"), false);
		}
		else if(this.guiFolder.listFiles().length == 0)
		{
			this.saveFile("test.yml", new File(this.guiFolder.getPath() + File.separator + "test.yml"), false);
		}
		
		this.loadFunctions();
		GuiApi.loadGuis();

		//AnvilNMS.init(); TODO

		this.noPermissionFunction = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-permission-function"));
		this.noPermissionGui = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-permission-gui"));
		this.noMoney = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-money"));
		this.noExp = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-exp"));
		this.noPoints = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-points"));
		this.noGui = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("no-gui"));
		this.getCommand("gui").setExecutor(new GUICommand(this));
		this.version = this.getConfig().getString("version");
		for(final String str : this.getConfig().getStringList("servers"))
		{
			ReplacerAPI.addReplacer(new Replacer("%" + str  + "-players%")
			{
				@Override
				public String replacement(String text, Player player)
				{
					return text.replace(this.getToReplace(), String.valueOf(serverPlayerCount.get(str)));
				}
			});
			this.serverPlayerCount.put(str, 0);
		}
		this.serverPlayerCount.put("ALL", 0);
		
		//check if placeholder is enabled
		placeHolderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
		
		
		ReplacerAPI.addReplacer(new PlayerReplacer("%player%"));
		ReplacerAPI.addReplacer(new OnlinePlayersReplacer("%online-players%"));
		ReplacerAPI.addReplacer(new UUIDReplacer("%uuid%"));
		ReplacerAPI.addReplacer(new PlayerLevelReplacer("%player-level%"));
		//setup vault
		if(Bukkit.getPluginManager().getPlugin("Vault") == null)
		{
			this.getLogger().log(Level.INFO, "Vault is not enabled");
			this.getLogger().log(Level.INFO, "The pay function is disabled");
		}
		else
		{
			this.setupEconomy();
			this.getLogger().log(Level.INFO, "Vault is enabled!");
			if(Bukkit.getPluginManager().getPlugin("Rankup") == null)
			{
				this.getLogger().log(Level.INFO, "Rankup is not enabled");
				this.getLogger().log(Level.INFO, "Rankup replacer is disabled");
			}
			else
			{
				this.getLogger().log(Level.INFO, "Rankup is enabled!");
				ReplacerAPI.addReplacer(new RankupReplacer("%rankup-percent%"));
			}
		}
		
		//setup citizens
		if(Bukkit.getPluginManager().getPlugin("Citizens") != null)
		{
			this.getLogger().log(Level.INFO, "Citizens hooking is enabled");
			Bukkit.getPluginManager().registerEvents(new EntityClickListener(this), this);
		}
		
		
		
		this.getLogger().log(Level.INFO, "Running version: " + Bukkit.getVersion().toLowerCase());
		
		//setup bungeecord
		if(this.version.equalsIgnoreCase("bungee"))
		{
			this.registerBungee();
		}
		else if(this.version.equalsIgnoreCase("redis"))
		{
			this.registerRedis();
		}
		else if(Bukkit.getVersion().toLowerCase().contains(this.version))
		{
			if(Bukkit.spigot() != null)
			{
				if(Bukkit.spigot().getConfig().getBoolean("settings.bungeecord"))
				{
					this.registerBungee();
				}
				else
				{
					this.bungeecord = false;
					this.getLogger().log(Level.INFO, "BungeeCord is not enabled!");
				}
			}
		}
		else
		{
			this.bungeecord = false;
			this.getLogger().log(Level.INFO, "BungeeCord is not enabled!");
		}
		
		
		this.getPlayerCountAll();
		
		//register listeners
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
		Bukkit.getPluginManager().registerEvents(new TemporaryInventoryClickListener(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryOpenListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		Bukkit.getPluginManager().registerEvents(new GuiApi(), this);
		
		
		
	}

	@Override
	public void onDisable()
	{
		
		this.cleanupGuis();
		this.cleanupCommands();

		this.economy = null;

		this.configFile = null;
		this.guiFolder = null;

		this.noPermissionFunction = null;
		this.noPermissionGui = null;
		this.noMoney = null;
		this.noExp = null;
		this.noGui = null;
		
		this.version = null;
		this.cm = null;
		
		this.invPlayers = null;
		this.registeredCommands = null;	
	}

	
	private void startPlayerCountTimer()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			@Override
			public void run()
			{
				for(String server : serverPlayerCount.keySet())
				{
					Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
					if(player != null)
					{
						ByteArrayDataOutput out = ByteStreams.newDataOutput();
						out.writeUTF("PlayerCount");
						out.writeUTF(server);
						String toSend = "BungeeCord";
						if(redis)
						{
							toSend = "RedisBungee";
						}
						player.sendPluginMessage(DynamicGUI.getPlugin(), toSend, out.toByteArray());
					}
				}
			}
		}
				
		,1L, 20L);
		
	}
	
	private void cleanupGuis()
	{
		try 
		{
			Field guis = GuiApi.class.getDeclaredField("guis");
			guis.setAccessible(true);
			guis.set(null, new ArrayList<GUI>());
		} 
		catch (NoSuchFieldException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void cleanupCommands()
	{
		if(this.getCommandMap() != null)
		{
			int amt = 0;
			for(String cmd : this.registeredCommands)
			{
				if(this.getCommand(cmd) != null)
				{
					if(this.getCommand(cmd).isRegistered())
					{
						this.getCommand(cmd).unregister(this.getCommandMap());
						this.getLogger().log(Level.INFO, cmd + " has been unregistered!");
						amt++;
					}
				}
			}

			if(this.registeredCommands.size() > 0)
				this.getLogger().log(Level.INFO, amt + " commands have been unregistered!");
		}
	}
	
	private void registerBungee()
	{
		this.getLogger().log(Level.INFO, "BungeeCord is enabled!");
		this.bungeecord = true;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		this.startPlayerCountTimer();
	}
	
	private void registerRedis()
	{
		this.getLogger().log(Level.INFO, "RedisBungee is enabled");
		this.redis = true;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "RedisBungee");
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "RedisBungee", this);
		this.startPlayerCountTimer();
		
	}
	
	private final CommandMap getCommandMap()
	{
		if (this.cm == null) 
		{
			try 
			{
				final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				f.setAccessible(true);
				this.cm = (CommandMap) f.get(Bukkit.getServer());
				return getCommandMap();
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace(); 
			}
		} 
		else if (this.cm != null) 
		{
			return this.cm; 
		}
		return getCommandMap();
	}

	public void loadCommand(String gui, String alias)
	{
		this.getLogger().log(Level.INFO, "Registered the command: " + alias + " for the gui " + gui);

		CustomCommand cmd = new CustomCommand(alias);			
		try 
		{
			Field commandField = this.getCommandMap().getClass().getDeclaredField("knownCommands");
			commandField.setAccessible(true);
			Map<String, Command> commands = (Map<String, Command>) commandField.get(this.getCommandMap());
			commands.keySet().remove(alias);
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) 
		{
			e.printStackTrace();
		}
		this.getCommandMap().register("", cmd);


		cmd.setExecutor(new CustomCommandExecutor(gui, alias));
		this.registeredCommands.add(alias);
	}

	private void loadFunctions()
	{
		FunctionApi.addFunction(new ConsoleCmdFunction("executec"));
		FunctionApi.addFunction(new ExpPayFunction("payexp"));
		FunctionApi.addFunction(new GuiFunction("gui"));
		FunctionApi.addFunction(new NoPermission("nopermission"));
		FunctionApi.addFunction(new PayFunction("pay"));
		FunctionApi.addFunction(new PermissionFunction("permission"));
		FunctionApi.addFunction(new PlayerCmdFunction("executep"));
		FunctionApi.addFunction(new PlayerMsgFunction("pmsg"));
		FunctionApi.addFunction(new SendFunction("send"));	
		FunctionApi.addFunction(new ServerBroadcastFunction("broadcast"));
		FunctionApi.addFunction(new ParticleFunction("particles"));
		FunctionApi.addFunction(new SoundFunction("sound"));
		FunctionApi.addFunction(new ReflectionStaticFunction("reflection-static"));
		FunctionApi.addFunction(new SetNameFunction("setname"));
		FunctionApi.addFunction(new SetLoreFunction("setlore"));
		FunctionApi.addFunction(new SetTypeFunction("settype"));
		FunctionApi.addFunction(new SetDataFunction("setdata"));
		FunctionApi.addFunction(new SetEnchantsFunction("setenchants"));
		FunctionApi.addFunction(new RemoveSlotFunction("removeslot"));
		FunctionApi.addFunction(new StatisticFunction("statistic"));
		FunctionApi.addFunction(new CheckLevelFunction("checklevel"));
		//FunctionApi.addFunction(new CustomTextFunction("customtext"));
	}

	private void saveFile(String resourcePath, File outFile, boolean replace) 
	{
		if (resourcePath == null || resourcePath.equals(""))
		{
			throw new IllegalArgumentException("Path cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = this.getResource(resourcePath);
		if (in == null) 
		{
			throw new IllegalArgumentException("The resource '" + resourcePath + "' cannot be found.");
		}

		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(this.getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

		if (!outDir.exists() && !outDir.mkdirs()) 
		{
			return;
		}

		try 
		{
			if (!outFile.exists() || replace) 
			{
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) 
				{
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
			} 
			else 
			{
				this.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
			}

		}
		catch (IOException ex) 
		{
			this.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
		}
	}

	public String getNoPermissionFunction()
	{
		return this.noPermissionFunction;
	}
	
	public String getNoPermissionGui()
	{
		return this.noPermissionGui;
	}
	
	public String getNoMoney()
	{
		return this.noMoney;
	}
	
	public String getNoExp()
	{
		return this.noExp;
	}
	
	public String getNoPoints()
	{
		return this.noPoints;
	}
	
	public String getNoGui()
	{
		return this.noGui;
	}


	public boolean getBungeeCord()
	{
		return this.bungeecord;
	}
	
	public boolean getRedisBungee()
	{
		return this.redis;
	}
	
	public static boolean getPlaceholderAPI()
	{
		return placeHolderAPI;
	}

	public int getPlayerCountAll()
	{
		if(this.getBungeeCord() || this.getRedisBungee())
		{
			return this.serverPlayerCount.get("ALL");
		}
		else 
		{
			return Bukkit.getOnlinePlayers().size();
		}
	}
	
	
	public DynamicGUIPlugin<T,U> getPlugin()
	{
		return this.plugin;
	}
	
	public EventManager getEventManager()
	{
		return this.eventManager;
	}
	
	public static void setInstance(DynamicGUI<?,?> instance)
	{
		if(DynamicGUI.instance == null)
		{
			DynamicGUI.instance = instance;
		}
	}
	
	public static DynamicGUI<?,?> getInstance() 
	{
		return instance;
	}
}
