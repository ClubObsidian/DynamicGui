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

package com.clubobsidian.dynamicgui.bukkit.command;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.event.plugin.DynamicGuiReloadEvent;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

public class BukkitDynamicGuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String first = args[0];
            if (sender.hasPermission("dynamicgui.reload")) {
                if (first.equalsIgnoreCase("reload")) {
                    sender.sendMessage("Guis have been reloaded");
                    GuiManager.get().reloadGuis(false);
                    DynamicGui.get().getEventBus().callEvent(new DynamicGuiReloadEvent());
                    return true;
                } else if (first.equals("forcereload")) {
                    sender.sendMessage("Guis have been force reloaded");
                    GuiManager.get().reloadGuis(true);
                    DynamicGui.get().getEventBus().callEvent(new DynamicGuiReloadEvent());
                    return true;
                }
            }
        } else if (args.length == 2) {
            String first = args[0];
            String second = args[1];
            if (first.equalsIgnoreCase("close")) {
                if (sender.hasPermission("dynamicgui.close")) {
                    if (second.equalsIgnoreCase("all")) {
                        sender.sendMessage("All open DynamicGui guis have been closed");
                        for (UUID uuid : GuiManager.get().getPlayerGuis().keySet()) {
                            Bukkit.getServer().getPlayer(uuid).closeInventory();
                        }
                        return true;
                    } else {
                        Player player = Bukkit.getServer().getPlayer(second);
                        if (player == null) {
                            sender.sendMessage("That player is not online, so their gui cannot be closed");
                            return true;
                        } else {

                            if (GuiManager.get().getPlayerGuis().get(player.getUniqueId()) != null) {
                                sender.sendMessage(player.getName() + "'s gui has been closed");
                                player.closeInventory();
                                return true;
                            } else {
                                sender.sendMessage(player.getName() + " did not have a DynamicGui gui open");
                                return true;
                            }
                        }
                    }
                }
            }
        } else if (args.length == 3) {
            String first = args[0];
            String second = args[1];
            String third = args[2];
            if (first.equalsIgnoreCase("close")) {
                if (second.equalsIgnoreCase("all")) {
                    Gui gui = GuiManager.get().getGui(third);
                    if (gui == null) {
                        sender.sendMessage("No gui can be found by that name");
                        return true;
                    } else {
                        sender.sendMessage("Guis of type " + third + " are now closed");
                        Iterator<Entry<UUID, Gui>> it = GuiManager.get().getPlayerGuis().entrySet().iterator();
                        while (it.hasNext()) {
                            Entry<UUID, Gui> next = it.next();
                            if (next.getValue().getName().equals(third)) {
                                Bukkit.getServer().getPlayer(next.getKey()).closeInventory();
                            }
                        }
                        return true;
                    }
                }
            }
        } else {
            sender.sendMessage("/dynamicgui reload");
            sender.sendMessage("/dynamicgui forcereload");
            sender.sendMessage("/dynamicgui close <player>");
            sender.sendMessage("/dynamicgui close <all> <name>");
            return true;
        }
        return true;
    }
}