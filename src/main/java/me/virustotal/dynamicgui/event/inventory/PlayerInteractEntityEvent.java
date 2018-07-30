package me.virustotal.dynamicgui.event.inventory;

import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

public class PlayerInteractEntityEvent<P,E> extends PlayerEvent<P> {

	private EntityWrapper<E> entityWrapper;
	public PlayerInteractEntityEvent(PlayerWrapper<P> playerWrapper, EntityWrapper<E> entityWrapper)
	{
		super(playerWrapper);
		this.entityWrapper = entityWrapper;
	}
	
	public EntityWrapper<E> getEntityWrapper()
	{
		return this.entityWrapper;
	}
}