/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.plugin.bukkit;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.permission.bukkit.FoundryPermission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.command.bukkit.BukkitDynamicGuiCommand;
import com.clubobsidian.dynamicgui.command.bukkit.BukkitGuiCommand;
import com.clubobsidian.dynamicgui.command.bukkit.CustomCommand;
import com.clubobsidian.dynamicgui.command.bukkit.CustomCommandExecutor;
import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.economy.bukkit.VaultEconomy;
import com.clubobsidian.dynamicgui.inject.module.PluginModule;
import com.clubobsidian.dynamicgui.listener.bukkit.EntityClickListener;
import com.clubobsidian.dynamicgui.listener.bukkit.InventoryInteractListener;
import com.clubobsidian.dynamicgui.listener.bukkit.InventoryCloseListener;
import com.clubobsidian.dynamicgui.listener.bukkit.InventoryOpenListener;
import com.clubobsidian.dynamicgui.listener.bukkit.PlayerInteractListener;
import com.clubobsidian.dynamicgui.logger.JavaLoggerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.manager.entity.bukkit.BukkitEntityManager;
import com.clubobsidian.dynamicgui.manager.inventory.bukkit.BukkitInventoryManager;
import com.clubobsidian.dynamicgui.manager.inventory.bukkit.BukkitItemStackManager;
import com.clubobsidian.dynamicgui.manager.material.bukkit.BukkitMaterialManager;
import com.clubobsidian.dynamicgui.manager.world.bukkit.BukkitLocationManager;
import com.clubobsidian.dynamicgui.permission.Permission;
import com.clubobsidian.dynamicgui.permission.bukkit.VaultPermission;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.registry.npc.citizens.CitizensRegistry;
import com.clubobsidian.dynamicgui.registry.replacer.impl.PlaceholderApiReplacerRegistry;
import com.clubobsidian.dynamicgui.server.bukkit.FakeBukkitServer;

public class BukkitPlugin extends JavaPlugin implements DynamicGuiPlugin {

	private File configFile;
	private File guiFolder;
	private File macroFolder;
	private Economy economy;
	private Permission permission;
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
		this.macroFolder = new File(this.getDataFolder(), "macros");
		
		new PluginModule()
		.setEntity(BukkitEntityManager.class)
		.setInventory(BukkitInventoryManager.class)
		.setItemStack(BukkitItemStackManager.class)
		.setLocation(BukkitLocationManager.class)
		.setMaterial(BukkitMaterialManager.class)
		.setLogger(new JavaLoggerWrapper<>(this.getLogger()))
		.setPlugin(this)
		.setServer(new FakeBukkitServer())
		.bootstrap();


		PluginManager pm = this.getServer().getPluginManager();

		boolean vault = false;
		boolean foundry = false;
		if(pm.getPlugin("Vault") != null)
		{
			vault = true;
		}
		if(pm.getPlugin("Foundry") != null)
		{
			foundry = true;
		}

		if(vault && foundry)
		{
			this.permission = new FoundryPermission();
		}
		else if(vault)
		{
			this.permission = new VaultPermission();
		}

		if(this.permission != null && !this.permission.setup())
		{
			this.permission = null;
		}

		if(permission == null)
		{
			this.getLogger().log(Level.SEVERE, "Vault is not installed, permissions will not work");
		}

		this.economy = new VaultEconomy();
		if(!this.economy.setup())
		{
			this.economy = null;
		}

		if(this.economy == null)
		{
			this.getLogger().log(Level.SEVERE, "Vault is not installed, economy functions will not work");
		}

		this.npcRegistries = new ArrayList<>();

		//Hack for adding citizens late
		//For some reason citizens sometimes will load after DynamicGui
		this.getServer().getScheduler().scheduleSyncDelayedTask(this, () ->
		{
			if(this.getServer().getPluginManager().getPlugin("Citizens") != null)
			{
				this.getNPCRegistries().add(new CitizensRegistry());
			}
		}, 1);
		
		if(this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
		{
			ReplacerManager.get().registerReplacerRegistry(new PlaceholderApiReplacerRegistry());
		}
		
		this.getCommand("gui").setExecutor(new BukkitGuiCommand());
		this.getCommand("dynamicgui").setExecutor(new BukkitDynamicGuiCommand());
		this.getServer().getPluginManager().registerEvents(new EntityClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryInteractListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryOpenListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
	}

	@Override
	public void onDisable()
	{
		this.stop();
	}
	
	@Override
	public void stop() 
	{
		DynamicGui.get().shutdown();
	}

	@Override
	public Economy getEconomy() 
	{
		return this.economy;
	}

	@Override
	public Permission getPermission() 
	{
		return this.permission;
	}
	
	@Override
	public List<NPCRegistry> getNPCRegistries() 
	{
		return this.npcRegistries;
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
	
	@Override
	public File getMacroFolder()
	{
		return this.macroFolder;
	}

	private final CommandMap getCommandMap()
	{
		if (this.commandMap == null) 
		{
			try 
			{
				final Field f = this.getServer().getClass().getDeclaredField("commandMap");
				f.setAccessible(true);
				this.commandMap = (CommandMap) f.get(this.getServer());
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
			Field commandField = SimpleCommandMap.class.getDeclaredField("knownCommands");
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