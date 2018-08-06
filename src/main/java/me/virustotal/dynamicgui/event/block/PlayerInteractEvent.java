package me.virustotal.dynamicgui.event.block;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.LocationEvent;
import me.virustotal.dynamicgui.event.player.Action;
import me.virustotal.dynamicgui.world.LocationWrapper;

public class PlayerInteractEvent extends LocationEvent {

	private Action action;
	public PlayerInteractEvent(PlayerWrapper<?> playerWrapper, LocationWrapper<?> locationWrapper, Action action) 
	{
		super(playerWrapper, locationWrapper);
		this.action = action;
	}
	
	public Action getAction()
	{
		return this.action;
	}
}