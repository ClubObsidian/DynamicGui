package me.virustotal.dynamicgui.event;

import com.clubobsidian.trident.Event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public class PlayerEvent<P> extends Event {

	private PlayerWrapper<P> playerWrapper;
	public PlayerEvent(PlayerWrapper<P> playerWrapper)
	{
		this.playerWrapper = playerWrapper;
	}
	
	public PlayerWrapper<P> getPlayerWrapper()
	{
		return this.playerWrapper;
	}
}