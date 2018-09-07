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
package com.clubobsidian.dynamicgui.replacer.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class OnlinePlayersReplacer extends Replacer {

	public OnlinePlayersReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> player)
	{
		return String.valueOf(DynamicGui.get().getServer().getGlobalPlayerCount());
	}
}
