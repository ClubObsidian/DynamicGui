package com.clubobsidian.dynamicgui.manager.material;

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGui;

public abstract class MaterialManager {

	private static MaterialManager instance;
	
	public static MaterialManager get()
	{
		if(instance == null)
		{
			instance = DynamicGui.get().getInjector().getInstance(MaterialManager.class);
		}
		
		return instance;
	}
	
	public abstract List<String> getMaterials();
	public abstract String normalizeMaterial(String material);

}