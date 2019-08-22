package com.clubobsidian.dynamicgui.replacer.animation;

import com.clubobsidian.dynamicgui.animation.AnimationHolder;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.replacer.Replacer;

public class AnimationReplacer extends Replacer {

	private AnimationHolder holder;
	public AnimationReplacer(String toReplace, AnimationHolder holder) 
	{
		super(toReplace);
		this.holder = holder;
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> playerWrapper) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
