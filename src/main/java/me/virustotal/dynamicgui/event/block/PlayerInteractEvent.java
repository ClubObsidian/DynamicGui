package me.virustotal.dynamicgui.event.block;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.BlockEvent;
import me.virustotal.dynamicgui.event.player.Action;
import me.virustotal.dynamicgui.world.BlockWrapper;

public class PlayerInteractEvent<T,U> extends BlockEvent<T,U> {

	private Action action;
	public PlayerInteractEvent(PlayerWrapper<T> playerWrapper, BlockWrapper<U> blockWrapper, Action action) 
	{
		super(playerWrapper, blockWrapper);
		this.action = action;
	}
	
	public Action getAction()
	{
		return this.action;
	}
	
}
