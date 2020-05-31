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
package com.clubobsidian.dynamicgui.function.impl.gui;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;

public class BackFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7851730396417693718L;

	public BackFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		Gui gui = null;
		FunctionOwner owner = this.getOwner();
		if(owner instanceof Slot)
		{
			Slot slot = (Slot) owner;
			gui = slot.getOwner();
		}
		else if(owner instanceof Gui)
		{
			gui = (Gui) owner;
		}
		
		
		Gui back = gui.getBack();
		if(back != null)
		{
			if(this.getData() != null)
			{
				try
				{
					
					Integer backAmount = Integer.parseInt(this.getData());
					for(int i = 1; i < backAmount; i++)
					{
						Gui nextBack = back.getBack();
						if(nextBack != null)
						{
							back = nextBack;
						}
					}
				}
				catch(NumberFormatException ex)
				{
					return false;
				}
			}
			
			GuiManager.get().openGui(playerWrapper, back);
			return true;
		}
		
		return false;
	}
}