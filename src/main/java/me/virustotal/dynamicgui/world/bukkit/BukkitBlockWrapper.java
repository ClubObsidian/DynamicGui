package me.virustotal.dynamicgui.world.bukkit;

import org.bukkit.Location;
import org.bukkit.block.Block;

import me.virustotal.dynamicgui.world.BlockWrapper;
import me.virustotal.dynamicgui.world.LocationWrapper;

public class BukkitBlockWrapper<T extends Block> extends BlockWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3409009393354178987L;

	public BukkitBlockWrapper(T block) 
	{
		super(block);
	}

	@Override
	public LocationWrapper<?> getLocation() 
	{
		return new BukkitLocationWrapper<Location>(this.getBlock().getLocation());
	}
}