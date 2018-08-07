package me.virustotal.dynamicgui.listener.sponge;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import me.virustotal.dynamicgui.event.player.Action;
import me.virustotal.dynamicgui.world.LocationWrapper;
import me.virustotal.dynamicgui.world.sponge.SpongeLocationWrapper;

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
				me.virustotal.dynamicgui.event.block.PlayerInteractEvent interactEvent = new me.virustotal.dynamicgui.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
				DynamicGUI.get().getEventManager().callEvent(interactEvent);
			}
		}
	}
}