/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.listener.sponge;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.event.player.Action;
import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

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
				LocationWrapper<?> locationWrapper = LocationManager.get().toLocationWrapper(location.get());
				com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent interactEvent = new com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent(playerWrapper, locationWrapper, action);
				DynamicGui.get().getEventBus().callEvent(interactEvent);
				if(interactEvent.isCanceled())
			 	{
			 		e.setCancelled(true);
			 	}
			}
		}
	}
}