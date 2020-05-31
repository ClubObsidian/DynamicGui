/*
   Copyright 2020 Club Obsidian and contributors.

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

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongeEntityWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;

public class EntityClickListener {

	@Listener
	public void onEntityClick(InteractEntityEvent e, @First Player player)
	{
		if(e.getTargetEntity() != null)
		{
			PlayerWrapper<Player> playerWrapper = new SpongePlayerWrapper<Player>(player);
			EntityWrapper<Entity> entityWrapper = new SpongeEntityWrapper<Entity>(e.getTargetEntity());

			DynamicGui.get().getEventBus().callEvent(new com.clubobsidian.dynamicgui.event.inventory.PlayerInteractEntityEvent(playerWrapper, entityWrapper));
		}
	}
}