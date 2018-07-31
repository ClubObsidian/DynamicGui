package me.virustotal.dynamicgui.entity.player.impl;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.network.ChannelBinding.RawDataChannel;
import org.spongepowered.api.text.Text;
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
	public void sendPluginMessage(DynamicGUIPlugin<?, ?> plugin, String channel, byte[] message) 
	{	
		RawDataChannel pluginChannel = Sponge.getGame().getChannelRegistrar().getOrCreateRaw(plugin, channel);
		pluginChannel.sendTo(this.getPlayer(), buf -> buf.writeByteArray(message));
	}

	@Override
	public void playSound(String sound, Float volume, Float pitch) 
	{
		Location<World> location = this.getPlayer().getLocation();
		Optional<SoundType> soundType = Sponge.getGame().getRegistry().getType(SoundType.class, sound);
		if(soundType.isPresent())
		{
			this.getPlayer().playSound(soundType.get(), location.getPosition(), volume, pitch, volume);
		}
	}

	@Override
	public void playEffect(String effect, int data) 
	{
		Location<World> location = this.getPlayer().getLocation();
		Optional<ParticleType> particleType = Sponge.getGame().getRegistry().getType(ParticleType.class, effect);
		if(particleType.isPresent())
		{
			ParticleEffect particleEffect = ParticleEffect
					.builder()
					.type(particleType.get())
					.build();
			location.getExtent().spawnParticles(particleEffect, location.getPosition());
		}	
	}
}