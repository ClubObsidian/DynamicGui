package me.virustotal.dynamicgui.event;

import com.clubobsidian.trident.Event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

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