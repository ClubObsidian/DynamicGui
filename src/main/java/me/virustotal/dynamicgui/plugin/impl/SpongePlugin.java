package me.virustotal.dynamicgui.plugin.impl;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.server.ServerType;

@Plugin(id = "dynamicgui", name = "DynamicGUI", version = "1.0")
public class SpongePlugin implements DynamicGUIPlugin {

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
		ServerType.setServerType(ServerType.SPONGE);
		
	}

	@Override
	public void stop() 
	{
		// TODO Auto-generated method stub
		
	}
	
	
}