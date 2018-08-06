package me.virustotal.dynamicgui.event.block;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.BlockEvent;
import me.virustotal.dynamicgui.event.player.Action;
import me.virustotal.dynamicgui.world.block.BlockWrapper;

public class PlayerInteractEvent extends BlockEvent {

	private Action action;
	public PlayerInteractEvent(PlayerWrapper<?> playerWrapper, BlockWrapper<?> blockWrapper, Action action) 
	{
		super(playerWrapper, blockWrapper);
		this.action = action;
	}
	
	public Action getAction()
	{
		return this.action;
	}
	
}
