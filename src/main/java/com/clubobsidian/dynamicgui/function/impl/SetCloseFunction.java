/*
 *    Copyright 2021 Club Obsidian and contributors.
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
package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;

public class SetCloseFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5671625221707551692L;

	public SetCloseFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		try
		{
			Boolean value = Boolean.valueOf(this.getData());
			if(value != null)
			{
				if(this.getOwner() instanceof Slot)
				{
					Slot slot = (Slot) this.getOwner();
					slot.setClose(value);
					return true;
				}
				else if(this.getOwner() instanceof Gui)
				{
					Gui gui = (Gui) this.getOwner();
					gui.setClose(value);
					return true;
				}
			}
		}
		catch(Exception ex)
		{
			DynamicGui.get().getLogger().info("Error parsing value " + this.getData() + " for setclose function");
			ex.printStackTrace();
		}
		return false;
	}
}
