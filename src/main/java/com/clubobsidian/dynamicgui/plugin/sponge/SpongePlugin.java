package com.clubobsidian.dynamicgui.plugin.sponge;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.command.sponge.SpongeGUICommand;
import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.listener.sponge.EntityClickListener;
import com.clubobsidian.dynamicgui.listener.sponge.InventoryClickListener;
import com.clubobsidian.dynamicgui.listener.sponge.InventoryCloseListener;
import com.clubobsidian.dynamicgui.listener.sponge.InventoryOpenListener;
import com.clubobsidian.dynamicgui.listener.sponge.PlayerInteractListener;
import com.clubobsidian.dynamicgui.logger.Sl4jLoggerWrapper;
import com.clubobsidian.dynamicgui.npc.NPC;
import com.clubobsidian.dynamicgui.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.server.sponge.FakeSpongeServer;
import com.google.inject.Inject;

import java.io.File;
import java.nio.file.Path;
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

@Plugin(id = "dynamicgui", name = "DynamicGUI", version = "1.0")
public class SpongePlugin implements DynamicGUIPlugin {

	@Inject
	private Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;
	
	private File guiFolder;
	private File dataFolder;
	private File configFile;
	
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
		
		DynamicGUI.createInstance(this, new FakeSpongeServer(), new Sl4jLoggerWrapper<Logger>(this.logger));
		CommandSpec guiSpec = CommandSpec.builder().description(Text.of("GUI command"))
				.executor(new SpongeGUICommand())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("gui"))))
				.build();
		
		Sponge.getGame().getCommandManager().register(this, guiSpec, "gui");
		
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
	public boolean isNPC(EntityWrapper<?> entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NPC getNPC(EntityWrapper<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Economy getEconomy() {
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
	public void createCommand(String guiName, String alias) {
		// TODO Auto-generated method stub
		
	}
}