/*
   Copyright 2018 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class GuiFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 848178368629667482L;
	
	public GuiFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> playerWrapper)
	{
		final String gui = this.getData();

		if(!GuiManager.get().hasGuiName(gui))
			return false;
		
		FunctionOwner owner = this.getOwner();
		if(owner instanceof Slot)
		{
			Slot slot = (Slot) owner;
			slot.setClose(false);
		}
		
		GuiManager.get().openGui(playerWrapper, gui);
		return true;
	}
}