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
package com.clubobsidian.dynamicgui.registry.replacer.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.Cooldown;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class MetadataReplacerRegistry implements ReplacerRegistry {

	private static MetadataReplacerRegistry instance;
	
	public static MetadataReplacerRegistry get()
	{
		if(instance == null)
		{
			instance = new MetadataReplacerRegistry();
		}
		
		return instance;
	}
	
	private static final String METADATA_PREFIX = "%metadata_";
	
	@Override
	public String replace(PlayerWrapper<?> playerWrapper, String text) 
	{
		Gui gui = GuiManager.get().getCurrentGui(playerWrapper);
		if(gui == null)
		{
			return text;
		}

		for(Map.Entry<String, String> entry : gui.getMetadata().entrySet())
		{
			String metadataKey = entry.getKey();
			String metadataValue = entry.getValue();
			String metadataReplacer = METADATA_PREFIX + metadataKey + "%";
			if(text.contains(metadataReplacer))
			{
				text = StringUtils.replace(text, metadataReplacer, metadataValue);
			}
		}
		
		return text;
	}
}