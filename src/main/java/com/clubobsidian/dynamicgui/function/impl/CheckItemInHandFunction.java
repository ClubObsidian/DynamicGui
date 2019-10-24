package com.clubobsidian.dynamicgui.function.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class CheckItemInHandFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2308186311331769892L;

	public CheckItemInHandFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		List<String> types = new ArrayList<>();
		types.add(this.getData());
		
		if(this.getData().contains(","))
		{
			types = Arrays.asList(this.getData().split(","));
		}
		ItemStackWrapper<?> item = playerWrapper.getItemInHand();
		if(item == null && types.contains("AIR"))
		{
			return true;
		}
		
		String type = item.getType();
		
		return types.contains(type);
	}
}