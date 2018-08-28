package com.clubobsidian.dynamicgui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.clubobsidian.dynamicgui.configuration.Configuration;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.impl.CheckLevelFunction;
import com.clubobsidian.dynamicgui.function.impl.ConsoleCmdFunction;
import com.clubobsidian.dynamicgui.function.impl.ExpPayFunction;
import com.clubobsidian.dynamicgui.function.impl.GuiFunction;
import com.clubobsidian.dynamicgui.function.impl.NoPermission;
import com.clubobsidian.dynamicgui.function.impl.ParticleFunction;
import com.clubobsidian.dynamicgui.function.impl.PayFunction;
import com.clubobsidian.dynamicgui.function.impl.PermissionFunction;
import com.clubobsidian.dynamicgui.function.impl.PlayerCmdFunction;
import com.clubobsidian.dynamicgui.function.impl.PlayerMsgFunction;
import com.clubobsidian.dynamicgui.function.impl.RemoveSlotFunction;
import com.clubobsidian.dynamicgui.function.impl.SendFunction;
import com.clubobsidian.dynamicgui.function.impl.ServerBroadcastFunction;
import com.clubobsidian.dynamicgui.function.impl.SetDataFunction;
import com.clubobsidian.dynamicgui.function.impl.SetEnchantsFunction;
import com.clubobsidian.dynamicgui.function.impl.SetLoreFunction;
import com.clubobsidian.dynamicgui.function.impl.SetNameFunction;
import com.clubobsidian.dynamicgui.function.impl.SetTypeFunction;
import com.clubobsidian.dynamicgui.function.impl.SoundFunction;
import com.clubobsidian.dynamicgui.function.impl.StatisticFunction;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.replacer.impl.OnlinePlayersReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.PlayerLevelReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.PlayerReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.UUIDReplacer;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.dynamicgui.util.ChatColor;

import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class DynamicGUI  {

	public static final String TAG = "DynamicGuiSlot";

	private static DynamicGUI instance = null;

	//Initialized on init
	private String noGui;
	private String version;
	private boolean bungeecord;
	private boolean redis;
	
	private EventManager eventManager;
	private DynamicGUIPlugin plugin;
	private FakeServer server;
	private LoggerWrapper<?> loggerWrapper;
	private DynamicGUI(DynamicGUIPlugin plugin, FakeServer server, LoggerWrapper<?> loggerWrapper)
	{
		this.eventManager = new JavaAssistEventManager();
		this.plugin = plugin;
		this.server = server;
		this.loggerWrapper = loggerWrapper;
	}

	private void init()
	{
		this.setupFileStructure();
		this.loadConfig();
		this.loadFunctions();
		this.loadGuis();
		this.checkForProxy();
		this.registerListeners();
		this.loadReplacers();
	}

	private void setupFileStructure()
	{
		if(!this.plugin.getDataFolder().exists())
		{
			this.plugin.getDataFolder().mkdirs();
		}

		if(!this.plugin.getGuiFolder().exists())
		{
			this.plugin.getGuiFolder().mkdirs();
		}
	}

	private void loadConfig()
	{
		if(!this.plugin.getConfigFile().exists())
		{
			try 
			{
				FileUtils
				.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("config.yml"), 
						this.plugin.getConfigFile());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		Configuration config = Configuration.load(this.plugin.getConfigFile());
		this.noGui = ChatColor.translateAlternateColorCodes('&', config.getString("no-gui"));
		this.version = config.getString("version").trim();
	}

	private void loadGuis()
	{
		GuiManager.get(); //Initialize manager
	}

	public void checkForProxy()
	{
		if(this.version.equalsIgnoreCase("bungee"))
		{
			this.registerBungee();
		}
		else if(this.version.equalsIgnoreCase("redis"))
		{
			this.registerRedis();
		}
		else
		{
			this.bungeecord = false;
			this.getLogger().info("BungeeCord is not enabled!");
		}
	}

	private void registerListeners() 
	{
		this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.EntityClickListener());
		this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.InventoryClickListener());
		this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.InventoryCloseListener());
		this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.InventoryOpenListener());
		this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.PlayerInteractListener());
	}

	private void loadFunctions()
	{
		FunctionManager.get().addFunction(new ConsoleCmdFunction("executec"));
		//FunctionApi.get().addFunction(new ExpPayFunction("payexp"));
		FunctionManager.get().addFunction(new GuiFunction("gui"));
		FunctionManager.get().addFunction(new NoPermission("nopermission"));
		FunctionManager.get().addFunction(new PayFunction("pay"));
		FunctionManager.get().addFunction(new PermissionFunction("permission"));
		FunctionManager.get().addFunction(new PlayerCmdFunction("executep"));
		FunctionManager.get().addFunction(new PlayerMsgFunction("pmsg"));
		FunctionManager.get().addFunction(new SendFunction("send"));	
		FunctionManager.get().addFunction(new ServerBroadcastFunction("broadcast"));
		FunctionManager.get().addFunction(new ParticleFunction("particles"));
		FunctionManager.get().addFunction(new SoundFunction("sound"));
		FunctionManager.get().addFunction(new SetNameFunction("setname"));
		FunctionManager.get().addFunction(new SetLoreFunction("setlore"));
		FunctionManager.get().addFunction(new SetTypeFunction("settype"));
		FunctionManager.get().addFunction(new SetDataFunction("setdata"));
		FunctionManager.get().addFunction(new SetEnchantsFunction("setenchants"));
		FunctionManager.get().addFunction(new RemoveSlotFunction("removeslot"));
		FunctionManager.get().addFunction(new StatisticFunction("statistic"));
		FunctionManager.get().addFunction(new CheckLevelFunction("checklevel"));
	}

	private void loadReplacers()
	{
		ReplacerManager.get().addReplacer(new PlayerReplacer("%player%"));
		ReplacerManager.get().addReplacer(new OnlinePlayersReplacer("%online-players%"));
		ReplacerManager.get().addReplacer(new UUIDReplacer("%uuid%"));
		ReplacerManager.get().addReplacer(new PlayerLevelReplacer("%player-level%"));
	}

	private List<String> registeredCommands = new ArrayList<>();

	//private CommandMap cm = null;



	private Map<String, Integer> serverPlayerCount = new HashMap<String, Integer>();

	//TODO - port to dynamicgui plugins
	/*@Override
	public void onDisable()
	{

		this.cleanupGuis();
		this.cleanupCommands();
	}*/


	private void startPlayerCountTimer()
	{
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				for(String server : serverPlayerCount.keySet())
				{
					PlayerWrapper<?> player = Iterables.getFirst(DynamicGUI.get().getServer().getOnlinePlayers(), null);
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
						player.sendPluginMessage(DynamicGUI.get().getPlugin(), toSend, out.toByteArray());
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
			Field guis = GuiManager.class.getDeclaredField("guis");
			guis.setAccessible(true);
			guis.set(null, new ArrayList<Gui>());
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

	/*private void cleanupCommands()
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
	}*/


	private void registerBungee()
	{
		this.bungeecord = true;
		this.getLogger().info("BungeeCord is enabled!");
		this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
		this.getServer().registerIncomingPluginChannel(this.getPlugin(), "BungeeCord");
		this.startPlayerCountTimer();
	}

	private void registerRedis()
	{
		this.redis = true;
		this.getLogger().info("RedisBungee is enabled");
		this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "RedisBungee");
		this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
		this.getServer().registerIncomingPluginChannel(this.getPlugin(), "RedisBungee");
		this.startPlayerCountTimer();
	}

	/*private final CommandMap getCommandMap()
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
		this.getPlugin().getLogger().log(Level.INFO, "Registered the command: " + alias + " for the gui " + gui);

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
	}*/

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

	public DynamicGUIPlugin getPlugin()
	{
		return this.plugin;
	}

	public EventManager getEventManager()
	{
		return this.eventManager;
	}

	public FakeServer getServer()
	{
		return this.server;
	}

	public LoggerWrapper<?> getLogger()
	{
		return this.loggerWrapper;
	}

	public Integer getGlobalServerPlayerCount()
	{
		Integer playerCount = 0;
		for(Integer count : this.serverPlayerCount.values())
		{
			playerCount += count;
		}
		return playerCount;
	}

	public Integer getServerPlayerCount(String server)
	{
		return this.serverPlayerCount.get(server);
	}

	public static DynamicGUI get() 
	{
		return instance;
	}

	public static DynamicGUI createInstance(DynamicGUIPlugin plugin, FakeServer server, LoggerWrapper<?> loggerWrapper)
	{
		if(DynamicGUI.instance == null)
		{
			DynamicGUI.instance = new DynamicGUI(plugin, server, loggerWrapper);
			DynamicGUI.instance.init();
			return DynamicGUI.instance;
		}
		return DynamicGUI.instance;
	}
}