package com.clubobsidian.dynamicgui.listener.sponge;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongeEntityWrapper;

public class EntityClickListener {

	@Listener
	public void onEntityClick(InteractEntityEvent e, @First Player player)
	{
		if(e.getTargetEntity() != null)
		{
			PlayerWrapper<Player> playerWrapper = new SpongePlayerWrapper<Player>(player);
			EntityWrapper<Entity> entityWrapper = new SpongeEntityWrapper<Entity>(e.getTargetEntity());

			DynamicGUI.get().getEventManager().callEvent(new com.clubobsidian.dynamicgui.event.inventory.PlayerInteractEntityEvent(playerWrapper, entityWrapper));
		}
	}
}