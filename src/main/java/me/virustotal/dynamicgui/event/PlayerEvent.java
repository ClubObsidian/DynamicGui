package me.virustotal.dynamicgui.event;

import com.clubobsidian.trident.Event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public class PlayerEvent<T> extends Event {

	private PlayerWrapper<T> playerWrapper;
	public PlayerEvent(PlayerWrapper<T> playerWrapper)
	{
		this.playerWrapper = playerWrapper;
	}
	
	public PlayerWrapper<T> getPlayerWrapper()
	{
		return this.playerWrapper;
	}
}