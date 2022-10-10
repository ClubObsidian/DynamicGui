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

package com.clubobsidian.dynamicgui.bukkit.platform;

import com.clubobsidian.dynamicgui.bukkit.entity.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.bukkit.scheduler.BukkitScheduler;
import com.clubobsidian.dynamicgui.bukkit.world.BukkitWorldWrapper;
import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.platform.PlatformType;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.proxy.Proxy;
import com.clubobsidian.dynamicgui.api.scheduler.Scheduler;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import com.clubobsidian.dynamicgui.api.world.WorldWrapper;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BukkitPlatform implements Platform {

    private final Scheduler scheduler;

    public BukkitPlatform(Plugin plugin) {
        this.scheduler = new BukkitScheduler(plugin);
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public boolean isMainThread() {
        return Bukkit.getServer().isPrimaryThread();
    }

    @Override
    public void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(message);
    }

    @Override
    public void broadcastJsonMessage(String json) {
        BaseComponent[] components = ComponentSerializer.parse(json);
        Bukkit.getServer().spigot().broadcast(components);
    }

    @Override
    public void dispatchServerCommand(String command) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    @Override
    public PlayerWrapper<?> getPlayer(UUID uuid) {
        return new BukkitPlayerWrapper<>(Bukkit.getServer().getPlayer(uuid));
    }

    @Override
    public PlayerWrapper<?> getPlayer(String name) {
        return new BukkitPlayerWrapper<>(Bukkit.getServer().getPlayer(name));
    }

    @Override
    public Collection<PlayerWrapper<?>> getOnlinePlayers() {
        List<PlayerWrapper<?>> onlinePlayers = new ArrayList<>();
        Bukkit.getServer().getOnlinePlayers().forEach(player -> onlinePlayers.add(new BukkitPlayerWrapper<Player>(player)));
        return onlinePlayers;
    }

    @Override
    public int getLocalPlayerCount() {
        return Bukkit.getServer().getOnlinePlayers().size();
    }

    @Override
    public PlatformType getType() {
        return PlatformType.BUKKIT;
    }

    @Override
    public void registerOutgoingPluginChannel(final DynamicGuiPlugin plugin, final String outGoingChannel) {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) plugin, outGoingChannel);
    }

    @Override
    public void registerIncomingPluginChannel(final DynamicGuiPlugin plugin, final String incomingChannel, final MessagingRunnable runnable) {
        PluginMessageListener listener = (channel, player, message) -> {
            if (channel.equals(incomingChannel)) {
                PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<>(player);
                runnable.run(playerWrapper, message);
            }
        };
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel((Plugin) plugin, incomingChannel, listener);
    }

    @Override
    public WorldWrapper<?> getWorld(String worldName) {
        World world = Bukkit.getServer().getWorld(worldName);
        if (world == null) {
            return null;
        }
        return new BukkitWorldWrapper(worldName);
    }

    @Override
    public boolean syncCommands() {
        Server server = Bukkit.getServer();
        Method syncCommands = ReflectionUtil.getMethod(server.getClass(), "syncCommands");
        if (syncCommands != null) {
            try {
                syncCommands.invoke(server);
                return true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}