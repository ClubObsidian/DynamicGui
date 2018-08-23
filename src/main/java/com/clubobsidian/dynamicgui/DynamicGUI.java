package com.clubobsidian.dynamicgui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.clubobsidian.dynamicgui.api.FunctionApi;
import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.api.ReplacerAPI;
import com.clubobsidian.dynamicgui.configuration.Configuration;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
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
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.objects.replacers.OnlinePlayersReplacer;
import com.clubobsidian.dynamicgui.objects.replacers.PlayerLevelReplacer;
import com.clubobsidian.dynamicgui.objects.replacers.PlayerReplacer;
import com.clubobsidian.dynamicgui.objects.replacers.UUIDReplacer;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
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
	
	private DynamicGUIPlugin<?,?> plugin;
	private EventManager eventManager;
	private FakeServer server;
	private LoggerWrapper<?> loggerWrapper;
	private DynamicGUI(DynamicGUIPlugin<?,?> plugin, FakeServer server, LoggerWrapper<?> loggerWrapper)
	{
		this.plugin = plugin;
		this.eventManager = new JavaAssistEventManager();
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
		this.noPermissionFunction = ChatColor.translateAlternateColorCodes('&', config.getString("no-permission-function"));
		this.noPermissionGui = ChatColor.translateAlternateColorCodes('&', config.getString("no-permission-gui"));
		this.noMoney = ChatColor.translateAlternateColorCodes('&', config.getString("no-money"));
		this.noExp = ChatColor.translateAlternateColorCodes('&', config.getString("no-exp"));
		this.noPoints = ChatColor.translateAlternateColorCodes('&', config.getString("no-points"));
		this.noGui = ChatColor.translateAlternateColorCodes('&', config.getString("no-gui"));
		this.version = config.getString("version");
	}

	private void loadGuis()
	{
		GuiApi.loadGuis();
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
		FunctionApi.addFunction(new ConsoleCmdFunction("executec"));
		//FunctionApi.addFunction(new ExpPayFunction("payexp"));
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
		FunctionApi.addFunction(new SetNameFunction("setname"));
		FunctionApi.addFunction(new SetLoreFunction("setlore"));
		FunctionApi.addFunction(new SetTypeFunction("settype"));
		FunctionApi.addFunction(new SetDataFunction("setdata"));
		FunctionApi.addFunction(new SetEnchantsFunction("setenchants"));
		FunctionApi.addFunction(new RemoveSlotFunction("removeslot"));
		FunctionApi.addFunction(new StatisticFunction("statistic"));
		FunctionApi.addFunction(new CheckLevelFunction("checklevel"));
	}
	
	private void loadReplacers()
	{
		ReplacerAPI.addReplacer(new PlayerReplacer("%player%"));
		ReplacerAPI.addReplacer(new OnlinePlayersReplacer("%online-players%"));
		ReplacerAPI.addReplacer(new UUIDReplacer("%uuid%"));
		ReplacerAPI.addReplacer(new PlayerLevelReplacer("%player-level%"));
	}

	private List<String> registeredCommands = new ArrayList<>();
	
	private String noPermissionFunction;
	private String noPermissionGui;
	private String noMoney;
	private String noExp;
	private String noPoints;
	private String noGui;
	
	private String version;
	
	//private CommandMap cm = null;
	
	private boolean bungeecord;
	private boolean redis;

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
	
	public DynamicGUIPlugin<?,?> getPlugin()
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
	
	public static DynamicGUI createInstance(DynamicGUIPlugin<?,?> plugin, FakeServer server, LoggerWrapper<?> loggerWrapper)
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