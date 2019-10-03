/*
   Copyright 2019 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.plugin.sponge;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.command.sponge.SpongeDynamicGuiCommand;
import com.clubobsidian.dynamicgui.command.sponge.SpongeGuiCommand;
import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.listener.sponge.EntityClickListener;
import com.clubobsidian.dynamicgui.listener.sponge.InventoryClickListener;
import com.clubobsidian.dynamicgui.listener.sponge.InventoryCloseListener;
import com.clubobsidian.dynamicgui.listener.sponge.InventoryOpenListener;
import com.clubobsidian.dynamicgui.listener.sponge.PlayerInteractListener;
import com.clubobsidian.dynamicgui.logger.Sl4jLoggerWrapper;
import com.clubobsidian.dynamicgui.permission.Permission;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.registry.npc.NPC;
import com.clubobsidian.dynamicgui.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.server.sponge.FakeSpongeServer;

import com.google.inject.Inject;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import org.slf4j.Logger;

@Plugin(id = "dynamicgui", name = "DynamicGui", version = "1.0")
public class SpongePlugin implements DynamicGuiPlugin {

	@Inject
	private Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;
	
	private File guiFolder;
	private File dataFolder;
	private File configFile;
	private File macroFolder;
	
	public final static String PLUGIN_ID = "dynamicgui";

	@Listener
	public void gameStart(GameStartedServerEvent e)
	{
		this.start();
	}
	
	@Listener
	public void gameStopped(GameStoppedServerEvent e)
	{
		this.stop();
	}
	
	@Override
	public void start() 
	{
		this.dataFolder = configDir.toFile();
		this.configFile = new File(this.dataFolder, "config.yml");
		this.guiFolder = new File(this.dataFolder, "guis");
		this.macroFolder = new File(this.dataFolder, "macros");
		
		DynamicGui.createInstance(this, new FakeSpongeServer(), new Sl4jLoggerWrapper<Logger>(this.logger));
		CommandSpec guiSpec = CommandSpec.builder().description(Text.of("GUI command"))
				.executor(new SpongeGuiCommand())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("gui"))))
				.build();
		
		Sponge.getGame().getCommandManager().register(this, guiSpec, "gui");
		
		CommandSpec dynamicGuiSpec = CommandSpec.builder().description(Text.of("DynamicGui command"))
				.executor(new SpongeDynamicGuiCommand())
				.arguments(GenericArguments.optional(GenericArguments.string(Text.of("sub"))),
				GenericArguments.optional(GenericArguments.string(Text.of("subtwo"))),
				GenericArguments.optional(GenericArguments.string(Text.of("subthree"))))
				.build();
		
		Sponge.getGame().getCommandManager().register(this, dynamicGuiSpec, "dynamicgui", "dyngui");
		
		Sponge.getEventManager().registerListeners(this, new EntityClickListener());
		Sponge.getEventManager().registerListeners(this, new InventoryClickListener());
		Sponge.getEventManager().registerListeners(this, new InventoryCloseListener());
		Sponge.getEventManager().registerListeners(this, new InventoryOpenListener());
		Sponge.getEventManager().registerListeners(this, new PlayerInteractListener());
	}

	@Override
	public void stop() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Economy getEconomy() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Permission getPermission()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NPCRegistry> getNPCRegistries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getGuiFolder() 
	{
		return this.guiFolder;
	}

	@Override
	public File getDataFolder() 
	{
		return this.dataFolder;
	}

	@Override
	public File getConfigFile() 
	{
		return this.configFile;
	}
	
	@Override
	public File getMacroFolder()
	{
		return this.macroFolder;
	}

	@Override
	public List<String> getRegisteredCommands() 
	{
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}
	
	@Override
	public void createCommand(String guiName, String alias) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadCommands() {
		// TODO Auto-generated method stub
		
	}
}