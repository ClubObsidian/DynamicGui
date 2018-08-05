package me.virustotal.dynamicgui.event.inventory;

import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

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