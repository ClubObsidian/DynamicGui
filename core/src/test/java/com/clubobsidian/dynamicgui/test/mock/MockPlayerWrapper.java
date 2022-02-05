package com.clubobsidian.dynamicgui.test.mock;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

import java.util.UUID;

public abstract class MockPlayerWrapper extends PlayerWrapper<MockPlayer> {

    public MockPlayerWrapper() {
        this(new MockPlayer());
    }

    public MockPlayerWrapper(MockPlayer player) {
        super(player);
    }

    public String getName() {
        return this.getPlayer().getName();
    }

    public UUID getUniqueId() {
        return this.getPlayer().getUniqueId();
    }

    public void chat(String message) {
        this.getPlayer().chat(message);
    }

    public void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    public boolean hasPermission(String permission) {
        return this.getPlayer().hasPermission(permission);
    }

    public boolean addPermission(String permission) {
        return this.getPlayer().addPermission(permission);
    }
}