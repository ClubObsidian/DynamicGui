package me.virustotal.dynamicgui.event.player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

public class PlayerQuitEvent<T> extends PlayerEvent<T> {

	public PlayerQuitEvent(PlayerWrapper<T> playerWrapper) 
	{
		super(playerWrapper);
	}
}