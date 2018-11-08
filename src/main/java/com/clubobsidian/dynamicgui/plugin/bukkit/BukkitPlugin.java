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
package com.clubobsidian.dynamicgui.plugin.bukkit;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.command.bukkit.BukkitDynamicGuiCommand;
import com.clubobsidian.dynamicgui.command.bukkit.BukkitGuiCommand;
import com.clubobsidian.dynamicgui.command.bukkit.CustomCommand;
import com.clubobsidian.dynamicgui.command.bukkit.CustomCommandExecutor;
import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.economy.bukkit.VaultEconomy;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.listener.bukkit.EntityClickListener;
import com.clubobsidian.dynamicgui.listener.bukkit.InventoryClickListener;
import com.clubobsidian.dynamicgui.listener.bukkit.InventoryCloseListener;
import com.clubobsidian.dynamicgui.listener.bukkit.InventoryOpenListener;
import com.clubobsidian.dynamicgui.listener.bukkit.PlayerInteractListener;
import com.clubobsidian.dynamicgui.logger.JavaLoggerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.registry.npc.NPC;
import com.clubobsidian.dynamicgui.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.registry.npc.citizens.CitizensRegistry;
import com.clubobsidian.dynamicgui.registry.replacer.impl.PlaceholderApiReplacerRegistry;
import com.clubobsidian.dynamicgui.server.bukkit.FakeBukkitServer;

public class BukkitPlugin extends JavaPlugin implements DynamicGuiPlugin {

	private File configFile;
	private File guiFolder;
	private Economy economy;
	private List<NPCRegistry> npcRegistries;
	private CommandMap commandMap;
	private List<String> registeredCommands;
	
	@Override
	public void onEnable()
	{
		this.start();
	}
	
	@Override
	public void start() 
	{
		this.registeredCommands = new ArrayList<>();
		this.configFile = new File(this.getDataFolder(), "config.yml");
		this.guiFolder = new File(this.getDataFolder(), "guis");
		
		DynamicGui.createInstance(this, new FakeBukkitServer(), new JavaLoggerWrapper<Logger>(this.getLogger()));
		this.economy = new VaultEconomy();
		if(!this.economy.setup())
		{
			this.economy = null;
		}
		
		this.npcRegistries = new ArrayList<>();
		
		if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null)
		{
			this.getNPCRegistries().add(new CitizensRegistry());
		}
		
		if(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
		{
			ReplacerManager.get().registerReplacerRegistry(new PlaceholderApiReplacerRegistry());
		}
		
		this.getCommand("gui").setExecutor(new BukkitGuiCommand());
		this.getCommand("dynamicgui").setExecutor(new BukkitDynamicGuiCommand());
		Bukkit.getServer().getPluginManager().registerEvents(new EntityClickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryOpenListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
	}

	@Override
	public void onDisable()
	{
		this.stop();
	}
	
	@Override
	public void stop() 
	{
		
		
	}

	//TODO
	/*@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) 
	{
		//System.out.println("received: " + channel);
		if (channel.equals("BungeeCord") || channel.equals("RedisBungee")) 
		{
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
			String packet = in.readUTF();
			if(packet != null)
			{
				if(packet.equals("PlayerCount"))
				{
					//System.out.println("player count");
					String server = in.readUTF();
					//System.out.println("server: " + server);
					if(this.serverPlayerCount.containsKey(server))
					{
						int playerCount = in.readInt();
						//System.out.println("count: " + playerCount);
						this.serverPlayerCount.put(server, playerCount);
					}
				}
			}
		}
	}*/

	@Override
	public Economy getEconomy() 
	{
		return this.economy;
	}

	@Override
	public List<NPCRegistry> getNPCRegistries() 
	{
		return this.npcRegistries;
	}

	@Override
	public boolean isNPC(EntityWrapper<?> entity) 
	{
		for(NPCRegistry registry : this.getNPCRegistries())
		{
			if(registry.isNPC(entity))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public NPC getNPC(EntityWrapper<?> entityWrapper) 
	{
		for(NPCRegistry registry : this.getNPCRegistries())
		{
			NPC npc = registry.getNPC(entityWrapper);
			if(npc != null)
			{
				return npc;
			}
		}
		return null;
	}

	@Override
	public File getConfigFile() 
	{
		return this.configFile;
	}
	
	@Override
	public File getGuiFolder() 
	{
		return this.guiFolder;
	}

	private final CommandMap getCommandMap()
	{
		if (this.commandMap == null) 
		{
			try 
			{
				final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				f.setAccessible(true);
				this.commandMap = (CommandMap) f.get(Bukkit.getServer());
				return this.commandMap;
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace(); 
			}
		} 
		else if (this.commandMap != null) 
		{
			return this.commandMap; 
		}
		return null;
	}

	public List<String> getRegisteredCommands()
	{
		return this.registeredCommands;
	}
	
	private void unregisterCommand(String alias)
	{
		try 
		{
			Field commandField = this.getCommandMap().getClass().getDeclaredField("knownCommands");
			commandField.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, Command> commands = (Map<String, Command>) commandField.get(this.getCommandMap());
			commands.keySet().remove(alias);
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void createCommand(String gui, String alias)
	{
		DynamicGui.get().getLogger().info("Registered the command \"" + alias + "\" for the gui " + gui);

		CustomCommand cmd = new CustomCommand(alias);			
		this.unregisterCommand(alias);
		
		this.getCommandMap().register("", cmd);


		cmd.setExecutor(new CustomCommandExecutor(gui));
		this.getRegisteredCommands().add(alias);
	}

	@Override
	public void unloadCommands() 
	{
		for(String cmd : this.getRegisteredCommands())
		{
			this.unregisterCommand(cmd);
		}
	}
}