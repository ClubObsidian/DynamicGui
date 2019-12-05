package com.clubobsidian.dynamicgui.registry.replacer.impl;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private Pattern pattern;
	public CooldownReplacerRegistry()
	{
		this.pattern = Pattern.compile(COOLDOWN_PREFIX + ".*%");
	}
	
	@Override
	public String replace(PlayerWrapper<?> playerWrapper, String text) 
	{
		UUID uuid = playerWrapper.getUniqueId();
		
		Collection<Cooldown> cooldowns = CooldownManager.get().getCooldowns(uuid);
		if(cooldowns == null && text.contains(COOLDOWN_PREFIX))
		{
			Matcher matcher = this.pattern.matcher(text);
			return matcher.replaceAll("-1");
		}
		
		for(Cooldown cooldown : cooldowns)
		{
			String cooldownName = cooldown.getName();
			String cooldownReplacer = COOLDOWN_PREFIX + cooldownName + "%";
			if(text.contains(cooldownReplacer))
			{
				Long cooldownRemaining = CooldownManager.get().getRemainingCooldown(playerWrapper, cooldownName);
				String remainingStr = String.valueOf(cooldownRemaining);
				text = StringUtils.replace(text, cooldownReplacer, remainingStr);
			}
		}
		
		return text;
	}
}