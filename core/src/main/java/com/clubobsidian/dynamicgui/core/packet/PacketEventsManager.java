/*
 *    Copyright 2018-2025 virustotalop
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

package com.clubobsidian.dynamicgui.core.packet;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.api.inventory.InventoryView;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.packet.PacketManager;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.core.event.inventory.Click;
import com.clubobsidian.dynamicgui.core.event.inventory.InventoryClickEvent;
import com.clubobsidian.dynamicgui.core.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.core.util.TextUtil;
import com.clubobsidian.trident.EventBus;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow.WindowClickType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCloseWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The packetevents implementation shared by every platform, a platform
 * only has to convert its items to packetevents items.
 */
public abstract class PacketEventsManager extends PacketManager {

    private final Map<UUID, PacketGuiSession> sessions = new ConcurrentHashMap<>();
    private final WindowTracker windowTracker = new WindowTracker();
    private final Platform platform;
    private final EventBus eventBus;
    private PacketListenerCommon listener;

    public PacketEventsManager(Platform platform, EventBus eventBus) {
        this.platform = platform;
        this.eventBus = eventBus;
    }

    /**
     * Converts a non-empty platform item to a packetevents item
     *
     * @param itemStackWrapper the item to convert
     * @return the packetevents item
     */
    protected abstract ItemStack toPacketItem(@NotNull ItemStackWrapper<?> itemStackWrapper);

    @Override
    public void start() {
        this.listener = PacketEvents.getAPI()
                .getEventManager()
                .registerListener(new PacketGuiListener(this), PacketListenerPriority.NORMAL);
    }

    @Override
    public void stop() {
        for (PacketGuiSession session : new ArrayList<>(this.sessions.values())) {
            PlayerWrapper<?> playerWrapper = this.platform.getPlayer(session.getUniqueId());
            if (playerWrapper != null) {
                this.close(playerWrapper, session.getInventoryWrapper());
            }
        }
        this.sessions.clear();
        this.windowTracker.clear();
        if (this.listener != null) {
            PacketEvents.getAPI().getEventManager().unregisterListener(this.listener);
            this.listener = null;
        }
    }

    @Override
    public InventoryWrapper<?> createPacketInventory(@NotNull Component title, @NotNull InventoryType type, int rows) {
        return new PacketInventoryWrapper(new PacketInventory(title, type, rows), this);
    }

    @Override
    public @Nullable InventoryWrapper<?> getOpenInventory(@NotNull UUID uuid) {
        PacketGuiSession session = this.sessions.get(uuid);
        return session == null ? null : session.getInventoryWrapper();
    }

    @Override
    public void sendSlot(@NotNull PlayerWrapper<?> playerWrapper, int slot, @NotNull ItemStackWrapper<?> itemStackWrapper) {
        WindowTracker.NativeWindow window = this.windowTracker.get(playerWrapper.getUniqueId());
        if (window == null) {
            return;
        }
        this.send(playerWrapper, new WrapperPlayServerSetSlot(
                window.windowId(),
                window.stateId(),
                slot,
                this.convert(itemStackWrapper)
        ));
    }

    public static boolean isEmpty(@Nullable ItemStackWrapper<?> itemStackWrapper) {
        return itemStackWrapper == null
                || itemStackWrapper.getItemStack() == null
                || Slot.IGNORE_MATERIAL.equalsIgnoreCase(itemStackWrapper.getType());
    }

    void open(PlayerWrapper<?> playerWrapper, PacketInventoryWrapper inventoryWrapper) {
        PacketInventory inventory = inventoryWrapper.getInventory();
        PacketMenuType menuType = inventory.getMenuType();
        if (menuType == null) {
            DynamicGui.get().getLogger().error("Unable to open packet inventory of type '%s'",
                    inventory.getType().toString());
            return;
        }
        UUID uuid = playerWrapper.getUniqueId();
        PacketGuiSession previous = this.sessions.remove(uuid);
        if (previous != null) { //The new window replaces the previous one on the client
            previous.getInventory().removeViewer(uuid);
            this.callClose(playerWrapper, previous);
        } else if (this.windowTracker.get(uuid) != null) {
            playerWrapper.closeInventory();
        }
        int lastStateId = previous == null ? 0 : previous.getStateId();
        PacketGuiSession session = new PacketGuiSession(uuid, inventoryWrapper, lastStateId);
        this.sessions.put(uuid, session);
        inventory.addViewer(uuid);
        this.sendOpenWindow(playerWrapper, session, menuType);
        this.sendWindowItems(playerWrapper, session);
    }

    void close(PlayerWrapper<?> playerWrapper, PacketInventoryWrapper inventoryWrapper) {
        PacketGuiSession session = this.sessions.get(playerWrapper.getUniqueId());
        if (session == null || session.getInventoryWrapper() != inventoryWrapper || !this.removeSession(session)) {
            return;
        }
        this.send(playerWrapper, new WrapperPlayServerCloseWindow(PacketGuiSession.WINDOW_ID));
        this.callClose(playerWrapper, session);
        this.resyncPlayerInventory(playerWrapper, session);
    }

    void setTitle(PlayerWrapper<?> playerWrapper, PacketInventoryWrapper inventoryWrapper, String title) {
        PacketGuiSession session = this.sessions.get(playerWrapper.getUniqueId());
        if (session == null || session.getInventoryWrapper() != inventoryWrapper) {
            return;
        }
        PacketInventory inventory = inventoryWrapper.getInventory();
        inventory.setTitle(TextUtil.toComponent(title));
        this.sendOpenWindow(playerWrapper, session, inventory.getMenuType());
        this.sendWindowItems(playerWrapper, session);
    }

    void setItem(PacketInventoryWrapper inventoryWrapper, int index, ItemStackWrapper<?> itemStackWrapper) {
        inventoryWrapper.getInventory().setItem(index, itemStackWrapper, this.convert(itemStackWrapper));
        for (UUID viewer : inventoryWrapper.getInventory().getViewers()) {
            PlayerWrapper<?> playerWrapper = this.platform.getPlayer(viewer);
            if (playerWrapper != null) {
                this.sendItem(playerWrapper, inventoryWrapper, index);
            }
        }
    }

    void sendItem(PlayerWrapper<?> playerWrapper, PacketInventoryWrapper inventoryWrapper, int index) {
        PacketGuiSession session = this.sessions.get(playerWrapper.getUniqueId());
        if (session == null || session.getInventoryWrapper() != inventoryWrapper) {
            return;
        }
        this.send(playerWrapper, new WrapperPlayServerSetSlot(
                PacketGuiSession.WINDOW_ID,
                session.nextStateId(),
                index,
                inventoryWrapper.getInventory().getPacketItem(index)
        ));
    }

    @Nullable
    PacketGuiSession getSession(UUID uuid) {
        return this.sessions.get(uuid);
    }

    WindowTracker getWindowTracker() {
        return this.windowTracker;
    }

    /**
     * Handles a click in a packet window, called from the netty thread
     */
    void handleClick(PacketGuiSession session, int slot, int button, WindowClickType type, @Nullable Integer stateId) {
        boolean stale = stateId != null && session.isStale(stateId);
        this.runSync(() -> {
            UUID uuid = session.getUniqueId();
            if (this.sessions.get(uuid) != session) {
                return;
            }
            PlayerWrapper<?> playerWrapper = this.platform.getPlayer(uuid);
            if (playerWrapper == null) {
                return;
            }
            PacketInventoryWrapper inventoryWrapper = session.getInventoryWrapper();
            if (!stale && slot >= 0 && slot < inventoryWrapper.getSize()) {
                Click click = PacketClickMapper.toClick(type, button);
                this.eventBus.callEvent(new InventoryClickEvent(
                        playerWrapper,
                        inventoryWrapper,
                        inventoryWrapper.getItem(slot),
                        slot,
                        click,
                        InventoryView.TOP
                ));
            }
            if (this.sessions.get(uuid) == session) { //Undo whatever the client predicted
                this.sendWindowItems(playerWrapper, session);
                playerWrapper.updateInventory();
            }
        });
    }

    /**
     * Ends a session that was closed by the client or replaced by the server, called from the netty thread
     */
    void endSession(PacketGuiSession session) {
        if (!this.removeSession(session)) {
            return;
        }
        this.runSync(() -> {
            PlayerWrapper<?> playerWrapper = this.platform.getPlayer(session.getUniqueId());
            if (playerWrapper != null) {
                this.callClose(playerWrapper, session);
                this.resyncPlayerInventory(playerWrapper, session);
            }
        });
    }

    void removeUser(UUID uuid) {
        PacketGuiSession session = this.sessions.get(uuid);
        if (session != null) {
            this.removeSession(session);
        }
        this.windowTracker.remove(uuid);
    }

    private boolean removeSession(PacketGuiSession session) {
        UUID uuid = session.getUniqueId();
        if (this.sessions.remove(uuid, session)) {
            session.getInventory().removeViewer(uuid);
            return true;
        }
        return false;
    }

    private void callClose(PlayerWrapper<?> playerWrapper, PacketGuiSession session) {
        this.eventBus.callEvent(new InventoryCloseEvent(playerWrapper, session.getInventoryWrapper()));
    }

    private void resyncPlayerInventory(PlayerWrapper<?> playerWrapper, PacketGuiSession session) {
        if (session.getInventory().coversPlayerInventory()) {
            playerWrapper.updateInventory();
        }
    }

    private void sendOpenWindow(PlayerWrapper<?> playerWrapper, PacketGuiSession session, PacketMenuType menuType) {
        this.send(playerWrapper, new WrapperPlayServerOpenWindow(
                PacketGuiSession.WINDOW_ID,
                menuType.getId(),
                session.getInventory().getTitle()
        ));
    }

    private void sendWindowItems(PlayerWrapper<?> playerWrapper, PacketGuiSession session) {
        PacketInventory inventory = session.getInventory();
        List<ItemStack> items = new ArrayList<>(inventory.getSize());
        for (int i = 0; i < inventory.getSize(); i++) {
            items.add(inventory.getPacketItem(i));
        }
        //The carried item is always empty, which also clears any item the client predicted on the cursor
        this.send(playerWrapper, new WrapperPlayServerWindowItems(
                PacketGuiSession.WINDOW_ID,
                session.nextStateId(),
                items,
                ItemStack.EMPTY
        ));
    }

    private ItemStack convert(@Nullable ItemStackWrapper<?> itemStackWrapper) {
        if (isEmpty(itemStackWrapper)) {
            return ItemStack.EMPTY;
        }
        return this.toPacketItem(itemStackWrapper);
    }

    private void runSync(Runnable runnable) {
        this.platform.getScheduler().runSyncDelayedTask(runnable, 0L);
    }

    private void send(PlayerWrapper<?> playerWrapper, PacketWrapper<?> wrapper) {
        PacketEvents.getAPI().getPlayerManager().sendPacket(playerWrapper.getNative(), wrapper);
    }
}
