package com.clubobsidian.dynamicgui.registry.replacer;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public interface ReplacerRegistry {

	
	public String replace(PlayerWrapper<?> playerWrapper, String text);
	
}
