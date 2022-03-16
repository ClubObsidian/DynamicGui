/*
 *    Copyright 2022 virustotalop and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.bukkit.entity;

import com.clubobsidian.dynamicgui.bukkit.inventory.BukkitInventoryWrapper;
import com.clubobsidian.dynamicgui.bukkit.inventory.BukkitItemStackWrapper;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.effect.ParticleWrapper;
import com.clubobsidian.dynamicgui.core.effect.SoundWrapper;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.core.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.world.LocationWrapper;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class BukkitPlayerWrapper<T extends Player> extends PlayerWrapper<T> {

    public BukkitPlayerWrapper(T player) {
        super(player);
    }

    @Override
    public String getName() {
        return this.getPlayer().getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.getPlayer().getUniqueId();
    }

    @Override
    public void chat(String message) {
        this.getPlayer().chat(message);
    }

    @Override
    public void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    @Override
    public void sendJsonMessage(String json) {
        BaseComponent[] components = ComponentSerializer.parse(json);
        this.getPlayer().spigot().sendMessage(components);
    }

    @Override
    public boolean hasPermission(String permission) {
        return DynamicGui.get().getPlugin().getPermission().hasPermission(this, permission);
    }

    @Override
    public boolean addPermission(String permission) {
        return DynamicGui.get().getPlugin().getPermission().addPermission(this, permission);
    }

    @Override
    public boolean removePermission(String permission) {
        return DynamicGui.get().getPlugin().getPermission().removePermission(this, permission);
    }

    @Override
    public int getExperience() {
        System.out.println("Total exp: " + this.getPlayer().getTotalExperience());
        return this.getPlayer().getTotalExperience();
    }

    @Override
    public void setExperience(int experience) {
        this.getPlayer().setTotalExperience(experience);
    }

    @Override
    public int getLevel() {
        return this.getPlayer().getLevel();
    }

    @Override
    public InventoryWrapper<Inventory> getOpenInventoryWrapper() {
        InventoryView openInventory = this.getPlayer().getOpenInventory();
        if (openInventory == null) {
            return null;
        }
        return new BukkitInventoryWrapper<Inventory>(openInventory.getTopInventory());
    }

    @Override
    public ItemStackWrapper<ItemStack> getItemInHand() {
        ItemStack hand = this.getPlayer().getInventory().getItemInHand();
        return new BukkitItemStackWrapper<ItemStack>(hand);
    }

    @Override
    public void closeInventory() {
        if (this.getPlayer().getOpenInventory() != null) {
            this.getPlayer().getOpenInventory().close();
        }
    }

    @Override
    public void openInventory(InventoryWrapper<?> inventoryWrapper) {
        Object inventory = inventoryWrapper.getInventory();
        if (inventory instanceof Inventory) {
            this.getPlayer().openInventory((Inventory) inventory);
        }
    }

    @Override
    public void sendPluginMessage(DynamicGuiPlugin plugin, String channel, byte[] message) {
        this.getPlayer().sendPluginMessage((Plugin) plugin, channel, message);
    }

    @Override
    public void playSound(SoundWrapper.SoundData soundData) {
        String sound = soundData.getSound();
        float volume = soundData.getVolume();
        float pitch = soundData.getPitch();
        Player player = this.getPlayer();
        Location playerLocation = player.getLocation();
        player.playSound(playerLocation, Sound.valueOf(sound), volume, pitch);
    }

    @Override
    public void playEffect(ParticleWrapper.ParticleData particleData) {
        String effect = particleData.getEffect();
        int extraData = particleData.getExtraData();
        Player player = this.getPlayer();
        Location playerLocation = player.getLocation();
        playerLocation.getWorld().playEffect(playerLocation, Effect.valueOf(effect), extraData);
    }

    @Override
    public void updateInventory() {
        this.getPlayer().updateInventory();
    }

    @Override
    public LocationWrapper<?> getLocation() {
        Location bukkitLoc = this.getPlayer().getLocation();
        return LocationManager.get().toLocationWrapper(bukkitLoc);
    }

    @Override
    public boolean isOnline() {
        return this.getPlayer().isOnline();
    }
}