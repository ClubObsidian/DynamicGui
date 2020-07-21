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
package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.GuiLoadEvent;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.util.FunctionUtil;
import com.clubobsidian.trident.EventHandler;

public class GuiListener {

	@EventHandler
	public void onGuiOpen(GuiLoadEvent event)
	{
		PlayerWrapper<?> wrapper = event.getPlayerWrapper();
		Gui gui = GuiManager.get().getCurrentGui(wrapper);
		PlayerWrapper<?> playerWrapper = event.getPlayerWrapper();
		boolean open = (gui != null);
		if(open)
		{
			FunctionUtil.tryFunctions(gui, FunctionType.SWITCH_MENU, playerWrapper);
		}
	}
}