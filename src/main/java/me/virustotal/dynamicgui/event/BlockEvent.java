package me.virustotal.dynamicgui.event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.world.BlockWrapper;

public class BlockEvent extends PlayerEvent {

	private BlockWrapper<?> blockWrapper;
	public BlockEvent(PlayerWrapper<?> playerWrapper, BlockWrapper<?> blockWrapper) 
	{
		super(playerWrapper);
		this.blockWrapper = blockWrapper;
	}
	
	public BlockWrapper<?> getBlockWrapper()
	{
		return this.blockWrapper;
	}
}
