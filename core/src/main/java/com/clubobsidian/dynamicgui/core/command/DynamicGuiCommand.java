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

package com.clubobsidian.dynamicgui.core.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.command.RegisteredCommand;
import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.Constant;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.event.plugin.DynamicGuiReloadEvent;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.platform.Platform;
import com.clubobsidian.dynamicgui.core.util.ChatColor;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DynamicGuiCommand implements RegisteredCommand {

    private final Platform platform;

    @Inject
    private DynamicGuiCommand(Platform platform) {
        this.platform = platform;
    }

    @CommandMethod("dynamicgui|dyngui reload")
    @CommandPermission(Constant.DYNAMIC_GUI_COMMAND_RELOAD_PERMISSION)
    private void reload(GuiCommandSender sender) {
        sender.sendMessage("Guis have been reloaded");
        GuiManager.get().reloadGuis(false);
        DynamicGui.get().getEventBus().callEvent(new DynamicGuiReloadEvent());
    }

    @CommandMethod("dynamicgui|dyngui forcereload")
    @CommandPermission(Constant.DYNAMIC_GUI_COMMAND_RELOAD_PERMISSION)
    private void forceReload(GuiCommandSender sender) {
        sender.sendMessage("Guis have been force reloaded");
        GuiManager.get().reloadGuis(true);
        DynamicGui.get().getEventBus().callEvent(new DynamicGuiReloadEvent());
    }

    @CommandMethod("dynamicgui|dyngui close all [guiName]")
    @CommandPermission(Constant.DYNAMIC_GUI_COMMAND_CLOSE_PERMISSION)
    private void closeAll(GuiCommandSender sender, @Argument("guiName") String guiName) {
        if(guiName == null) {
            sender.sendMessage("All open DynamicGui guis have been closed");
            for (UUID uuid : GuiManager.get().getPlayerGuis().keySet()) {
                PlayerWrapper<?> playerWrapper = this.platform.getPlayer(uuid);
                if (playerWrapper != null) {
                    playerWrapper.closeInventory();
                }
            }
        } else {
            Gui gui = GuiManager.get().getGui(guiName);
            if (gui == null) {
                sender.sendMessage("No gui can be found by that name");
            } else {
                sender.sendMessage(String.format("Guis of type %s are now closed", guiName));
                Iterator<Map.Entry<UUID, Gui>> it = GuiManager.get().getPlayerGuis().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<UUID, Gui> next = it.next();
                    UUID uuid = next.getKey();
                    PlayerWrapper<?> playerWrapper = this.platform.getPlayer(uuid);
                    if (playerWrapper != null) {
                        playerWrapper.closeInventory();
                    }
                }
            }
        }
    }

    @CommandMethod("dynamicgui|dyngui close <playerName>")
    @CommandPermission(Constant.DYNAMIC_GUI_COMMAND_CLOSE_PERMISSION)
    private void closePlayer(GuiCommandSender sender, @Argument("playerName") String playerName) {
        PlayerWrapper<?> player = this.platform.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage("That player is not online, so their gui cannot be closed");
        } else {
            if (GuiManager.get().getPlayerGuis().get(player.getUniqueId()) != null) {
                sender.sendMessage(String.format("%s's gui has been closed", playerName));
                player.closeInventory();
            } else {
                sender.sendMessage(String.format("%s did not have a DynamicGui gui open", playerName));
            }
        }
    }

    @CommandMethod("dynamicgui|dyngui list")
    @CommandPermission(Constant.DYNAMIC_GUI_LIST_PERMISSION)
    private void guiList(GuiCommandSender sender) {
        List<String> guiNames = GuiManager
                .get()
                .getGuis()
                .stream()
                .map((gui) -> "&a" + gui.getName())
                .collect(Collectors.toList());
        String built = ChatColor.translateAlternateColorCodes(String.join(" &f," + guiNames));
        sender.sendMessage(built);
    }
}
