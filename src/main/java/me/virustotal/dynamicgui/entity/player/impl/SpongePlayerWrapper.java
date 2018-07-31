package me.virustotal.dynamicgui.entity.player.impl;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.impl.SpongeInventoryWrapper;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

public class SpongePlayerWrapper<T extends Player> extends PlayerWrapper<T> {

	public SpongePlayerWrapper(T player) 
	{
		super(player);
	}

	@Override
	public String getName() 
	{
		return this.getPlayer().getName();
	}

	@Override
	public UUID getUniqueId() 
	{
		return this.getPlayer().getUniqueId();
	}

	@Override
	public void chat(String message) 
	{
		this.getPlayer().simulateChat(Text.of(message), Sponge.getCauseStackManager()
				.pushCauseFrame()
				.pushCause(this.getPlayer())
				.addContext(EventContextKeys.PLAYER_SIMULATED, this.getPlayer().getProfile())
				.getCurrentCause());
	}

	@Override
	public void sendMessage(String message) 
	{
		this.getPlayer().sendMessage(Text.of(message));
	}

	@Override
	public boolean hasPermission(String permission) 
	{
		return this.getPlayer().hasPermission(permission);
	}

	@Override
	public int getExperience() 
	{
		Optional<ExperienceHolderData> holder = this.getPlayer().get(ExperienceHolderData.class);
		if(!holder.isPresent())
			return -1;
		
		return holder.get().totalExperience().get();
	}

	@Override
	public void setExperience(int experience) 
	{
		Optional<ExperienceHolderData> holder = this.getPlayer().get(ExperienceHolderData.class);
		if(!holder.isPresent())
			return;
		
		holder.get().totalExperience().set(experience);
	}

	@Override
	public int getLevel() 
	{
		Optional<ExperienceHolderData> holder = this.getPlayer().get(ExperienceHolderData.class);
		if(!holder.isPresent())
			return -1;
		
		return holder.get().level().get();
	}

	@Override
	public InventoryWrapper<Inventory> getOpenInventoryWrapper() 
	{
		return new SpongeInventoryWrapper<Inventory>(this.getPlayer().getOpenInventory().get());
	}

	@Override
	public void closeInventory() 
	{
		this.getPlayer().closeInventory();
	}

	@Override
	public void openInventory(InventoryWrapper<?> inventoryWrapper) 
	{
		this.getPlayer().openInventory((Inventory) inventoryWrapper.getInventory());	
	}

	@Override
	public void sendPluginMessage(DynamicGUIPlugin<?, ?> plugin, String channel, byte[] message) {
		
		Sponge.getGame().getChannelRegistrar().getOrCreateRaw(plugin, channel).sendTo(this.getPlayer(), message);
	}

	@Override
	public void playSound(String sound, Float volume, Float pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playEffect(String effect, int data) 
	{
		Location<World> location = this.getPlayer().getLocation();
		
		location.getExtent().spawnParticles(, position);
	}



}
