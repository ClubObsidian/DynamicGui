package com.clubobsidian.dynamicgui.listener.sponge;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.event.player.Action;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.dynamicgui.world.sponge.SpongeLocationWrapper;

public class PlayerInteractListener {

	@Listener
	public void playerInteract(InteractBlockEvent e, @First Player player)
	{
		Optional<Location<World>> location = e.getTargetBlock().getLocation();
		if(location.isPresent())
		{
			if(location.get().getBlockType() != BlockTypes.AIR)
			{

				Action action = Action.LEFT_CLICK_BLOCK;
				if(e instanceof InteractBlockEvent.Secondary)
				{
					action = Action.RIGHT_CLICK_BLOCK;
				}
				PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
				LocationWrapper<?> locationWrapper = new SpongeLocationWrapper<Location<World>>(location.get());
				com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent interactEvent = new com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
				DynamicGUI.get().getEventManager().callEvent(interactEvent);
			}
		}
	}
}