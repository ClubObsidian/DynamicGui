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
package com.clubobsidian.dynamicgui.function.impl.meta;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;

public class SetMetadataFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2376716466726111306L;

	public SetMetadataFunction(String name) 
	{
		super(name);
	}

	//SetMetadata (index),key,value
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		if(this.getData() == null)
		{
			return false;
		}
		else if(!this.getData().contains(","))
		{
			return false;
		}
		
		String[] split = this.getData().split(",");
		FunctionOwner owner = this.getOwner();
		Slot slot = null;
		
		if(owner instanceof Gui && split.length == 3)
		{
			int index = -1;
			String first = split[0];
			try 
			{
				index = Integer.valueOf(first);
			}
			catch(Exception ex)
			{
				DynamicGui.get().getLogger().error("Invalid index " + first + " in HasMetadata function");
				return false;
			}
			Gui gui = (Gui) owner;
			for(Slot s : gui.getSlots())
			{
				if(s.getIndex() == index)
				{
					slot = s;
					break;
				}
			}
		}
		else if(owner instanceof Slot)
		{
			slot = (Slot) this.getOwner();
		}
		if(slot != null)
		{
			String key = split[1];
			String value = split[2];
			slot.getMetadata().put(key, value);
			return true;
		}
		
		return false;
	}	
}