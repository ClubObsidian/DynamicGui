package me.virustotal.dynamicgui.event.player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

public class PlayerKickEvent extends PlayerEvent {

	public PlayerKickEvent(PlayerWrapper<?> playerWrapper) 
	{
		super(playerWrapper);
	}
}