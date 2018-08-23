package com.clubobsidian.dynamicgui.event.block;


import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.LocationEvent;
import com.clubobsidian.dynamicgui.event.player.Action;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

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