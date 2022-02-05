package com.clubobsidian.dynamicgui.test.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class MockPlayer {

    private final String name;
    private final UUID uuid;
    private final List<String> incomingChat = new ArrayList<>();
    private final List<String> outgoingChat = new ArrayList<>();
    private final Collection<String> permissions = new HashSet<>();

    public MockPlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public MockPlayer() {
        this("test", UUID.randomUUID());
    }

    public String getName() {
        return this.name;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void chat(String message) {
        this.outgoingChat.add(message);
    }

    public void sendMessage(String message) {
        this.incomingChat.add(message);
    }

    public List<String> getIncomingChat() {
        return this.incomingChat;
    }

    public List<String> getOutgoingChat() {
        return this.outgoingChat;
    }

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }

    public boolean addPermission(String permission) {
        return this.permissions.add(permission);
    }

}