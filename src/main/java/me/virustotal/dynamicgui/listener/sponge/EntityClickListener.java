package me.virustotal.dynamicgui.listener.sponge;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import me.virustotal.dynamicgui.entity.sponge.SpongeEntityWrapper;

public class EntityClickListener {

	@Listener
	public void onEntityClick(InteractEntityEvent e)
	{
		if(e.getSource() instanceof Player)
		{
			if(e.getTargetEntity() != null)
			{
				PlayerWrapper<Player> playerWrapper = new SpongePlayerWrapper<Player>((Player) e.getSource());
				EntityWrapper<Entity> entityWrapper = new SpongeEntityWrapper<Entity>(e.getTargetEntity());
			
				DynamicGUI.get().getEventManager().callEvent(new me.virustotal.dynamicgui.event.inventory.PlayerInteractEntityEvent(playerWrapper, entityWrapper));
			}
		}
	}
}