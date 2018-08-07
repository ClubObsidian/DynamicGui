package me.virustotal.dynamicgui.plugin.sponge;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.npc.NPC;
import me.virustotal.dynamicgui.npc.NPCRegistry;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.server.ServerType;

@Plugin(id = "dynamicgui", name = "DynamicGUI", version = "1.0")
public class SpongePlugin<T extends Player, U extends Entity> implements DynamicGUIPlugin<T,U> {

	@Inject
	private org.slf4j.Logger sl4jLogger;
	
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
	public NPC<U> getNPC(EntityWrapper<?> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Economy getEconomy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NPCRegistry<U>> getNPCRegistries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getGuiFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getDataFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getConfigFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createCommand(String guiName, String alias) {
		// TODO Auto-generated method stub
		
	}
}