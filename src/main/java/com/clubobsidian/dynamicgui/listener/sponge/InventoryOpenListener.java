/*   Copyright 2018 Club Obsidian and contributors.

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

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;

public class InventoryOpenListener {

	@Listener
	public void onInventoryOpen(InteractInventoryEvent.Open e, @First Player player)
	{
		PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
		InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(e.getTargetInventory());
		com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent inventoryOpenEvent = new com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent(playerWrapper, inventoryWrapper);
		DynamicGui.get().getEventManager().callEvent(inventoryOpenEvent);
	}
}
