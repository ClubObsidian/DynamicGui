package com.clubobsidian.dynamicgui.event.inventory;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.PlayerEvent;

public class PlayerInteractEntityEvent extends PlayerEvent {

	private EntityWrapper<?> entityWrapper;
	public PlayerInteractEntityEvent(PlayerWrapper<?> playerWrapper, EntityWrapper<?> entityWrapper)
	{
		super(playerWrapper);
		this.entityWrapper = entityWrapper;
	}
	
	public EntityWrapper<?> getEntityWrapper()
	{
		return this.entityWrapper;
	}
}