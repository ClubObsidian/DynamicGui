/*
 *    Copyright 2018-2023 virustotalop
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

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.effect.ParticleWrapper;
import com.clubobsidian.dynamicgui.api.effect.SoundWrapper;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import com.clubobsidian.dynamicgui.bukkit.inventory.BukkitInventoryWrapper;
import com.clubobsidian.dynamicgui.bukkit.inventory.BukkitItemStackWrapper;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class BukkitPlayerWrapper<T extends Player> extends PlayerWrapper<T> {

    private static final Method CONTENTS = ReflectionUtil
            .getMethodLossy(PlayerInventory.class, "getStorageContents");

    public BukkitPlayerWrapper(@NotNull T player) {
        super(player);
    }

    @Override
    public String getName() {
        return this.getNative().getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.getNative().getUniqueId();
    }

    @Override
    public void chat(@NotNull String message) {
        this.getNative().chat(message);
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.getNative().sendMessage(Objects.requireNonNull(message));
    }

    @Override
    public void sendJsonMessage(@NotNull String json) {
        Objects.requireNonNull(json);
        BaseComponent[] components = ComponentSerializer.parse(json);
        this.getNative().spigot().sendMessage(components);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        Objects.requireNonNull(permission);
        return DynamicGui.get().getPermission().hasPermission(this, permission);
    }

    @Override
    public boolean addPermission(@NotNull String permission) {
        Objects.requireNonNull(permission);
        return DynamicGui.get().getPermission().addPermission(this, permission);
    }

    @Override
    public boolean removePermission(@NotNull String permission) {
        Objects.requireNonNull(permission);
        return DynamicGui.get().getPermission().removePermission(this, permission);
    }

    @Override
    public int getExperience() {
        return this.getNative().getTotalExperience();
    }

    @Override
    public void setExperience(int experience) {
        this.getNative().setTotalExperience(experience);
    }

    @Override
    public int getLevel() {
        return this.getNative().getLevel();
    }

    @Override
    public InventoryWrapper<Inventory> getOpenInventoryWrapper() {
        InventoryView openInventory = this.getNative().getOpenInventory();
        if (openInventory == null) {
            return null;
        }
        return new BukkitInventoryWrapper<>(openInventory.getTopInventory());
    }

    @Override
    public ItemStackWrapper<ItemStack> getItemInHand() {
        ItemStack hand = this.getNative().getInventory().getItemInHand();
        return new BukkitItemStackWrapper<>(hand);
    }

    @Override
    public void closeInventory() {
        if (this.getNative().getOpenInventory() != null) {
            this.getNative().getOpenInventory().close();
        }
    }

    @Override
    public void openInventory(@NotNull InventoryWrapper<?> inventoryWrapper) {
        Objects.requireNonNull(inventoryWrapper);
        Object inventory = inventoryWrapper.getInventory();
        if (inventory instanceof Inventory) {
            this.getNative().openInventory((Inventory) inventory);
        }
    }

    @Override
    public void sendPluginMessage(@NotNull String channel, byte[] message) {
        this.getNative().sendPluginMessage((Plugin) DynamicGui.get().getPlugin(), channel, message);
    }

    @Override
    public void playSound(SoundWrapper.@NotNull SoundData data) {
        Objects.requireNonNull(data);
        String sound = data.getSound();
        float volume = data.getVolume();
        float pitch = data.getPitch();
        Player player = this.getNative();
        Location playerLocation = player.getLocation();
        player.playSound(playerLocation, Sound.valueOf(sound), volume, pitch);
    }

    @Override
    public void playEffect(ParticleWrapper.@NotNull ParticleData data) {
        Objects.requireNonNull(data);
        String effect = data.getEffect();
        int extraData = data.getExtraData();
        Player player = this.getNative();
        Location playerLocation = player.getLocation();
        playerLocation.getWorld().playEffect(playerLocation, Effect.valueOf(effect), extraData);
    }

    @Override
    public void updateInventory() {
        this.getNative().updateInventory();
    }

    @Override
    public LocationWrapper<?> getLocation() {
        Location bukkitLoc = this.getNative().getLocation();
        return LocationManager.get().toLocationWrapper(bukkitLoc);
    }

    @Override
    public boolean isOnline() {
        return this.getNative().isOnline();
    }

    @Override
    public String getSkinTexture() {
        try {
            Object profile = this.getNative().getClass().getDeclaredMethod("getProfile").invoke(this.getNative());
            Object properties = profile.getClass()
                    .getDeclaredMethod("getProperties")
                    .invoke(profile);
            Class<?> forwardingMap = Class.forName(
                    new String(new byte[]{'c', 'o', 'm', '.'}) +
                            "google." +
                            "common." +
                            "collect." +
                            "ForwardingMultimap"); //We relocate guava so we have to do this
            Object property = null;
            for (Method m : forwardingMap.getDeclaredMethods()) {
                if (m.getName().equals("get")) {
                    property = ((Collection) (m.invoke(properties, "textures")))
                            .stream().findFirst().orElse(null);
                    break;
                }
            }
            if (property == null) {
                return null;
            }
            return (String) ReflectionUtil.getDeclaredField(property.getClass(), "value").get(property);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                 | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateCursor() {
        this.getNative().setItemOnCursor(this.getNative().getItemOnCursor());
    }

    @Override
    public int getOpenInventorySlots() {
        int slots = 0;
        PlayerInventory inventory = this.getNative().getInventory();
        try {
            ItemStack[] contents = CONTENTS == null
                    ? inventory.getContents() : (ItemStack[]) CONTENTS.invoke(inventory);
            for (ItemStack item : contents) {
                if (item == null || item.getType() == Material.AIR) {
                    slots += 1;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return slots;
    }
}