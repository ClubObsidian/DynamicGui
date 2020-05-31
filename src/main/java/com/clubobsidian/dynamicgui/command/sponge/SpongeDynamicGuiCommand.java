/*
   Copyright 2020 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.command.sponge;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.event.DynamicGuiReloadEvent;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class SpongeDynamicGuiCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		Optional<String> first = args.getOne("sub");
		Optional<String> second = args.getOne("subtwo");
		Optional<String> third = args.getOne("subthree");
		if(first.isPresent() && !second.isPresent() && !third.isPresent())
		{
			if(src.hasPermission("dynamicgui.reload"))
			{
				if(first.get().equalsIgnoreCase("reload"))
				{

					src.sendMessage(Text.of("Guis have been reloaded"));
					GuiManager.get().reloadGuis(false);
					DynamicGui.get().getEventBus().callEvent(new DynamicGuiReloadEvent());
					return CommandResult.success();
				}
				else if(first.get().equalsIgnoreCase("forcereload"))
				{

					src.sendMessage(Text.of("Guis have been force reloaded"));
					GuiManager.get().reloadGuis(true);
					DynamicGui.get().getEventBus().callEvent(new DynamicGuiReloadEvent());
					return CommandResult.success();
				}
			}
		}

		if(second.isPresent() && !third.isPresent())
		{
			if(first.get().equalsIgnoreCase("close"))
			{
				if(src.hasPermission("dynamicgui.close"))
				{
					if(second.get().equalsIgnoreCase("all"))
					{
						src.sendMessage(Text.of("All open DynamicGui guis have been closed"));
						for(UUID uuid : GuiManager.get().getPlayerGuis().keySet())
						{
							Optional<Player> player = Sponge.getServer().getPlayer(uuid);
							if(player.isPresent())
							{
								player.get().closeInventory();
							}
						}
						return CommandResult.success();
					}
					else
					{
						Optional<Player> player = Sponge.getServer().getPlayer(second.get());
						if(!player.isPresent())
						{
							src.sendMessage(Text.of("That player is not online, so their gui cannot be closed"));
							return CommandResult.success();
						}
						else
						{

							if(GuiManager.get().getPlayerGuis().get(player.get().getUniqueId()) != null)
							{
								src.sendMessage(Text.of(player.get().getName() + "'s gui has been closed"));
								player.get().closeInventory();
								return CommandResult.success();
							}
							else
							{
								src.sendMessage(Text.of(player.get().getName() + " did not have a DynamicGui gui open"));
								return CommandResult.success();
							}
						}
					}
				}
			}
		}
		
		
		if(third.isPresent())
		{
			if(first.get().equalsIgnoreCase("close"))
			{
				if(second.get().equalsIgnoreCase("all"))
				{
					Gui gui = GuiManager.get().getGuiByName(third.get());
					if(gui == null)
					{
						src.sendMessage(Text.of("No gui can be found by that name"));
						return CommandResult.success();
					}
					else
					{
						src.sendMessage(Text.of("Guis of type " + third.get() + " are now closed"));
						Iterator<Entry<UUID, Gui>> it = GuiManager.get().getPlayerGuis().entrySet().iterator();
						while(it.hasNext())
						{
							Entry<UUID, Gui> next = it.next();
							if(next.getValue().getName().equals(third.get()))
							{
								Optional<Player> player = Sponge.getServer().getPlayer(next.getKey());
								if(player.isPresent())
								{
									player.get().closeInventory();
								}
							}
						}
						return CommandResult.success();
					}
				}
			}
		}
		else
		{
			src.sendMessage(Text.of("/dynamicgui reload"));
			src.sendMessage(Text.of("/dynamicgui forcereload"));
			src.sendMessage(Text.of("/dynamicgui close <player>"));
			src.sendMessage(Text.of("/dynamicgui close <all> <name>"));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}
}