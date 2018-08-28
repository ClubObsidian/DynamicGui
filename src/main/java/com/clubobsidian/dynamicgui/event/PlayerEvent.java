package com.clubobsidian.dynamicgui.event;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.trident.Event;

public class PlayerEvent extends Event {

	private PlayerWrapper<?> playerWrapper;
	public PlayerEvent(PlayerWrapper<?> playerWrapper)
	{
		this.playerWrapper = playerWrapper;
	}
	
	public PlayerWrapper<?> getPlayerWrapper()
	{
		return this.playerWrapper;
	}
}