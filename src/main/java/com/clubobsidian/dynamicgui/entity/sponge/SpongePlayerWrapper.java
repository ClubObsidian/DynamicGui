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
package com.clubobsidian.dynamicgui.entity.sponge;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryTransformations;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.network.ChannelBinding.RawDataChannel;
import org.spongepowered.api.statistic.BlockStatistic;
import org.spongepowered.api.statistic.StatisticTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.util.Statistic;

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
		Container container = this.getPlayer().getOpenInventory().get();
		
		Iterator<Inventory> it = container.iterator();
		while(it.hasNext())
		{
			Inventory next = it.next();
			DynamicGui.get().getLogger().info("From sponge player wrapper:  " + next.getClass() + "  " + next + "  " + next.totalItems() + "  " + next.getArchetype() + "  " + next.getInventoryProperty(InventoryTitle.class).isPresent());
		}
		
		return new SpongeInventoryWrapper<Inventory>(container.transform(InventoryTransformations.PLAYER_MAIN_HOTBAR_FIRST));
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
	public void sendPluginMessage(DynamicGuiPlugin plugin, String channel, byte[] message) 
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

	@Override
	public int getStatistic(Statistic statistic) 
	{
		Optional<org.spongepowered.api.statistic.Statistic> spongeStatistic = Sponge.getGame().getRegistry().getType(org.spongepowered.api.statistic.Statistic.class, statistic.getSpongeID());
		if(spongeStatistic.isPresent())
		{
			Optional<Long> data = this.getPlayer().getStatisticData().get(spongeStatistic.get());
			if(data.isPresent())
			{
				if(data.get() <= Integer.MAX_VALUE)
				{
					return data.get().intValue();
				}
			}
		}
		return -1;
	}

	@Override
	public int getStatistic(Statistic statistic, String data) 
	{
		/*if(statistic == Statistic.MINE_BLOCK)
		{
			Optional<BlockType> blockType = Sponge.getGame().getRegistry().getType(BlockType.class, data);
			if(blockType.isPresent())
			{
				Optional<BlockStatistic> spongeStatistic = Sponge.getGame().getRegistry().getBlockStatistic(StatisticTypes.BLOCKS_BROKEN, blockType.get()));	
				if(spongeStatistic.isPresent())
				{
					spongeStatistic.get().
				}
			}
		}
		else if(statistic == Statistic.KILL_ENTITY)
		{
			
		}*/
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateInventory() {
		// TODO Auto-generated method stub
	}
}
