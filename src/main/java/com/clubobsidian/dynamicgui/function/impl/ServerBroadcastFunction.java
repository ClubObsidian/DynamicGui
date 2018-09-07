/*   Copyright 2018 Club Obsidian and contributors.

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

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.util.ChatColor;


public class ServerBroadcastFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8252199196221271208L;
	
	public ServerBroadcastFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		DynamicGui.get().getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',  ReplacerManager.get().replace(this.getData(), playerWrapper)));
		return true;
	}
}
