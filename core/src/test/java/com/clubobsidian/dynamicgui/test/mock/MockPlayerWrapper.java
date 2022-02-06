package com.clubobsidian.dynamicgui.test.mock;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

import java.util.UUID;

public abstract class MockPlayerWrapper extends PlayerWrapper<MockPlayer> {

    private ItemStackWrapper<?> hand;

    public MockPlayerWrapper() {
        this(new MockPlayer());
    }

    public MockPlayerWrapper(MockPlayer player) {
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
    public boolean hasPermission(String permission) {
        return this.getPlayer().hasPermission(permission);
    }

    @Override
    public boolean addPermission(String permission) {
        return this.getPlayer().addPermission(permission);
    }

    @Override
    public ItemStackWrapper<?> getItemInHand() {
        return this.getPlayer().getItemInHand();
    }

    public void setItemInHand(ItemStackWrapper<?> hand) {
        this.getPlayer().setItemInHand(hand);
    }
}