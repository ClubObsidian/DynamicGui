package me.virustotal.dynamicgui.event.player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;

public class PlayerKickEvent<T> extends PlayerEvent<T> {

	public PlayerKickEvent(PlayerWrapper<T> playerWrapper) 
	{
		super(playerWrapper);
	}
}