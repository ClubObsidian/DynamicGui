package me.virustotal.dynamicgui.event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.world.BlockWrapper;

public class BlockEvent<T,U> extends PlayerEvent<T> {

	private BlockWrapper<U> blockWrapper;
	public BlockEvent(PlayerWrapper<T> playerWrapper, BlockWrapper<U> blockWrapper) 
	{
		super(playerWrapper);
		this.blockWrapper = blockWrapper;
	}
	
	public BlockWrapper<U> getBlockWrapper()
	{
		return this.blockWrapper;
	}
}
