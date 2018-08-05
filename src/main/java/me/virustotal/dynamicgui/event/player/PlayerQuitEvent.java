package me.virustotal.dynamicgui.event.player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

public class PlayerQuitEvent extends PlayerEvent {

	public PlayerQuitEvent(PlayerWrapper<?> playerWrapper) 
	{
		super(playerWrapper);
	}
}