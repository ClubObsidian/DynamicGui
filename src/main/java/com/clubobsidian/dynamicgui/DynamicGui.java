/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

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
import com.clubobsidian.dynamicgui.function.impl.RandomFunction;
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
import com.clubobsidian.dynamicgui.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.registry.replacer.impl.DynamicGuiReplacerRegistry;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.dynamicgui.util.ChatColor;

import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

import com.clubobsidian.wrappy.Configuration;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class DynamicGui  {

	private static DynamicGui instance = null;

	//Initialized on init
	private String noGui;
	private String version;
	private boolean bungeecord;
	private boolean redis;
	private Map<String, Integer> serverPlayerCount;
	
	private EventManager eventManager;
	private DynamicGuiPlugin plugin;
	private FakeServer server;
	private LoggerWrapper<?> loggerWrapper;
	private DynamicGui(DynamicGuiPlugin plugin, FakeServer server, LoggerWrapper<?> loggerWrapper)
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
		ReplacerManager.get().registerReplacerRegistry(DynamicGuiReplacerRegistry.get());
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
		MessagingRunnable runnable = new MessagingRunnable()
		{
			@Override
			public void run(PlayerWrapper<?> playerWrapper, byte[] message) 
			{
				ByteArrayDataInput in = ByteStreams.newDataInput(message);
				String packet = in.readUTF();
				if(packet != null)
				{
					if(packet.equals("PlayerCount"))
					{
						String server = in.readUTF();
						int playerCount = in.readInt();
						serverPlayerCount.put(server, playerCount);
					}
				}
			}
		};
		
		if(this.version.equalsIgnoreCase("bungee"))
		{
			this.bungeecord = true;
			this.getLogger().info("BungeeCord is enabled!");
			this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
			this.getServer().registerIncomingPluginChannel(this.getPlugin(), "BungeeCord", runnable);
		}
		else if(this.version.equalsIgnoreCase("redis"))
		{
			this.redis = true;
			this.getLogger().info("RedisBungee is enabled");
			this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "RedisBungee");
			this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
			this.getServer().registerIncomingPluginChannel(this.getPlugin(), "RedisBungee", runnable);
		}
		else
		{
			this.bungeecord = false;
			this.getLogger().info("BungeeCord is not enabled!");
		}
		if(this.bungeecord || this.redis)
		{
			this.serverPlayerCount = new HashMap<>();
			this.startPlayerCountTimer();
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
		FunctionManager.get().addFunction(new RandomFunction("random"));
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
					PlayerWrapper<?> player = Iterables.getFirst(DynamicGui.get().getServer().getOnlinePlayers(), null);
					if(player != null)
					{
						ByteArrayDataOutput out = ByteStreams.newDataOutput();
						out.writeUTF("PlayerCount");
						out.writeUTF(server);
						String sendTo = "BungeeCord";
						if(redis)
						{
							sendTo = "RedisBungee";
						}
						player.sendPluginMessage(DynamicGui.get().getPlugin(), sendTo, out.toByteArray());
					}
				}
			}
		}		
		,1L, 20L);
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

	public DynamicGuiPlugin getPlugin()
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
		return this.serverPlayerCount.get("ALL");	
	}

	public Integer getServerPlayerCount(String server)
	{
		return this.serverPlayerCount.get(server);
	}

	public static DynamicGui get() 
	{
		return instance;
	}

	public static DynamicGui createInstance(DynamicGuiPlugin plugin, FakeServer server, LoggerWrapper<?> loggerWrapper)
	{
		if(DynamicGui.instance == null)
		{
			DynamicGui.instance = new DynamicGui(plugin, server, loggerWrapper);
			DynamicGui.instance.init();
			return DynamicGui.instance;
		}
		return DynamicGui.instance;
	}
}
