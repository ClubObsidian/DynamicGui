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

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.UserDisconnectEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCloseWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCloseWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenWindow;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPlayerInventory;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Listens for window packets on the netty thread. Gui logic is handed to
 * {@link PacketEventsManager} which runs it on the platform's main thread.
 */
public class PacketGuiListener implements PacketListener {

    private static final int PLAYER_WINDOW_ID = 0;
    private static final int LEGACY_PLAYER_INVENTORY_WINDOW_ID = -2;

    private final PacketEventsManager manager;

    public PacketGuiListener(PacketEventsManager manager) {
        this.manager = manager;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        PacketTypeCommon type = event.getPacketType();
        if (type != PacketType.Play.Client.CLICK_WINDOW && type != PacketType.Play.Client.CLOSE_WINDOW) {
            return;
        }
        UUID uuid = event.getUser().getUUID();
        if (uuid == null) {
            return;
        }
        if (type == PacketType.Play.Client.CLICK_WINDOW) {
            this.onClick(event, uuid);
        } else {
            this.onClientClose(event, uuid);
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        PacketTypeCommon type = event.getPacketType();
        if (type != PacketType.Play.Server.OPEN_WINDOW
                && type != PacketType.Play.Server.CLOSE_WINDOW
                && type != PacketType.Play.Server.WINDOW_ITEMS
                && type != PacketType.Play.Server.SET_SLOT
                && type != PacketType.Play.Server.SET_PLAYER_INVENTORY
                && type != PacketType.Play.Server.RESPAWN
                && type != PacketType.Play.Server.DEATH_COMBAT_EVENT) {
            return;
        }
        UUID uuid = event.getUser().getUUID();
        if (uuid == null) {
            return;
        }
        PacketGuiSession session = this.manager.getSession(uuid);
        if (type == PacketType.Play.Server.OPEN_WINDOW) {
            this.onServerOpen(event, uuid, session);
        } else if (type == PacketType.Play.Server.CLOSE_WINDOW) {
            this.onServerClose(event, uuid, session);
        } else if (type == PacketType.Play.Server.WINDOW_ITEMS) {
            this.onWindowItems(event, uuid, session);
        } else if (type == PacketType.Play.Server.SET_SLOT) {
            this.onSetSlot(event, uuid, session);
        } else if (type == PacketType.Play.Server.SET_PLAYER_INVENTORY) {
            this.onSetPlayerInventory(event, session);
        } else { //Respawning and dying close the client's screen
            this.manager.getWindowTracker().remove(uuid);
            if (session != null) {
                this.manager.endSession(session);
            }
        }
    }

    @Override
    public void onUserDisconnect(UserDisconnectEvent event) {
        UUID uuid = event.getUser().getUUID();
        if (uuid == null) { //Connections that never logged in such as server list pings
            return;
        }
        this.manager.removeUser(uuid);
    }

    private void onClick(PacketReceiveEvent event, UUID uuid) {
        WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
        if (click.getWindowId() != PacketGuiSession.WINDOW_ID) {
            return;
        }
        //The server never knew about the window so it should never see the click
        event.setCancelled(true);
        PacketGuiSession session = this.manager.getSession(uuid);
        if (session == null) {
            return;
        }
        this.manager.handleClick(
                session,
                click.getSlot(),
                click.getButton(),
                click.getWindowClickType(),
                click.getStateId().orElse(null)
        );
    }

    private void onClientClose(PacketReceiveEvent event, UUID uuid) {
        int windowId = new WrapperPlayClientCloseWindow(event).getWindowId();
        if (windowId != PacketGuiSession.WINDOW_ID) {
            this.manager.getWindowTracker().close(uuid, windowId);
            return;
        }
        event.setCancelled(true);
        PacketGuiSession session = this.manager.getSession(uuid);
        if (session != null) {
            this.manager.endSession(session);
        }
    }

    private void onServerOpen(PacketSendEvent event, UUID uuid, PacketGuiSession session) {
        int windowId = new WrapperPlayServerOpenWindow(event).getContainerId();
        if (windowId == PacketGuiSession.WINDOW_ID) {
            return;
        }
        this.manager.getWindowTracker().open(uuid, windowId);
        if (session != null) { //A real window replaced the packet window
            this.manager.endSession(session);
        }
    }

    private void onServerClose(PacketSendEvent event, UUID uuid, PacketGuiSession session) {
        int windowId = new WrapperPlayServerCloseWindow(event).getWindowId();
        if (windowId == PacketGuiSession.WINDOW_ID) {
            return;
        }
        if (this.manager.getWindowTracker().close(uuid, windowId)) { //The native window closed before opening
            return;
        }
        if (session != null) { //The server closed whatever screen the client has open
            this.manager.endSession(session);
        }
    }

    private void onWindowItems(PacketSendEvent event, UUID uuid, PacketGuiSession session) {
        WrapperPlayServerWindowItems windowItems = new WrapperPlayServerWindowItems(event);
        int windowId = windowItems.getWindowId();
        if (windowId == PacketGuiSession.WINDOW_ID) {
            return;
        }
        if (windowId != PLAYER_WINDOW_ID) {
            this.manager.getWindowTracker().updateState(uuid, windowId, windowItems.getStateId());
            return;
        }
        if (session == null || !session.getInventory().coversPlayerInventory()) {
            return;
        }
        List<ItemStack> items = new ArrayList<>(windowItems.getItems());
        boolean changed = false;
        for (int slot = 0; slot < items.size(); slot++) {
            ItemStack guiItem = this.getCoveredItem(session, PlayerSlotMapper.fromPlayerWindowSlot(slot));
            if (guiItem != null) {
                items.set(slot, guiItem);
                changed = true;
            }
        }
        if (changed) {
            windowItems.setItems(items);
            event.markForReEncode(true);
        }
    }

    private void onSetSlot(PacketSendEvent event, UUID uuid, PacketGuiSession session) {
        WrapperPlayServerSetSlot setSlot = new WrapperPlayServerSetSlot(event);
        int windowId = setSlot.getWindowId();
        if (windowId == PacketGuiSession.WINDOW_ID) {
            return;
        }
        int inventoryIndex;
        if (windowId == PLAYER_WINDOW_ID) {
            inventoryIndex = PlayerSlotMapper.fromPlayerWindowSlot(setSlot.getSlot());
        } else if (windowId == LEGACY_PLAYER_INVENTORY_WINDOW_ID) {
            inventoryIndex = setSlot.getSlot();
        } else {
            if (windowId > PLAYER_WINDOW_ID) {
                this.manager.getWindowTracker().updateState(uuid, windowId, setSlot.getStateId());
            }
            return;
        }
        ItemStack guiItem = this.getCoveredItem(session, inventoryIndex);
        if (guiItem != null) {
            setSlot.setItem(guiItem);
            event.markForReEncode(true);
        }
    }

    private void onSetPlayerInventory(PacketSendEvent event, PacketGuiSession session) {
        WrapperPlayServerSetPlayerInventory setPlayerInventory = new WrapperPlayServerSetPlayerInventory(event);
        ItemStack guiItem = this.getCoveredItem(session, setPlayerInventory.getSlot());
        if (guiItem != null) {
            setPlayerInventory.setStack(guiItem);
            event.markForReEncode(true);
        }
    }

    /**
     * Gets the gui item shown in a player inventory slot, player inventory updates would
     * otherwise overwrite the gui's items in the player inventory part of the window.
     *
     * @return the gui item or null if the gui does not cover the slot
     */
    private ItemStack getCoveredItem(PacketGuiSession session, int inventoryIndex) {
        if (session == null || inventoryIndex == PlayerSlotMapper.NO_SLOT) {
            return null;
        }
        PacketInventory inventory = session.getInventory();
        int windowSlot = PlayerSlotMapper.toWindowSlot(inventory.getWindowSlots(), inventoryIndex);
        if (!inventory.coversPlayerSlot(windowSlot)) {
            return null;
        }
        return inventory.getPacketItem(windowSlot);
    }
}
