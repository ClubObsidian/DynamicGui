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
package com.clubobsidian.dynamicgui.registry.replacer.impl;

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.Cooldown;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;

public class CooldownReplacerRegistry implements ReplacerRegistry {

	private static CooldownReplacerRegistry instance;
	
	public static CooldownReplacerRegistry get()
	{
		if(instance == null)
		{
			instance = new CooldownReplacerRegistry();
		}
		
		return instance;
	}
	
	private static final String COOLDOWN_PREFIX = "%cooldown_";
	
	@Override
	public String replace(PlayerWrapper<?> playerWrapper, String text) 
	{
		UUID uuid = playerWrapper.getUniqueId();
		
		Collection<Cooldown> cooldowns = CooldownManager.get().getCooldowns(uuid);
		if(cooldowns == null)
		{
			return text;
		}
		
		for(Cooldown cooldown : cooldowns)
		{
			String cooldownName = cooldown.getName();
			String cooldownReplacer = COOLDOWN_PREFIX + cooldownName;
			if(text.contains(cooldownReplacer))
			{
				long seconds = CooldownManager.get().getRemainingCooldown(playerWrapper, cooldownName) / 1000;
				
				String hoursReplacer = COOLDOWN_PREFIX + cooldownName + "_hours%";
				if(text.contains(hoursReplacer))
				{
					long hours = seconds / 3600;
					text = StringUtils.replace(text, hoursReplacer, String.valueOf(hours));
					seconds -= hours * 3600;
				}
				
				String minutesReplacer = COOLDOWN_PREFIX + cooldownName + "_minutes%";
				if(text.contains(minutesReplacer))
				{
					long minutes = seconds / 60;
					text = StringUtils.replace(text, minutesReplacer, String.valueOf(minutes));
					seconds -= minutes * 60;
				}
				
				String secondsReplacer = COOLDOWN_PREFIX + cooldownName + "_seconds%";
				if(text.contains(secondsReplacer))
				{
					text = StringUtils.replace(text, secondsReplacer, String.valueOf(seconds));
				}
			}
		}
		
		return text;
	}
}