package com.clubobsidian.dynamicgui.function.impl;

import java.util.Random;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class RandomFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8056953555096911217L;

	public RandomFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		try
		{
			if(this.getData().contains("-"))
			{
				String[] split = this.getData().split("-");
				if(split.length == 2)
				{
					final int end = Integer.parseInt(split[0]);
					final int win = Integer.parseInt(split[1]);
					final Random rand = new Random();
					rand.setSeed(System.currentTimeMillis());
					int generate = rand.nextInt(end) + 1;
					return generate == win;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
}