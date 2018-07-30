package me.virustotal.dynamicgui.event.inventory;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

public class InventoryClickEvent<P,E> extends PlayerEvent<P> {

	public InventoryClickEvent(PlayerWrapper<P> playerWrapper)
	{
		super(playerWrapper);
	}
}