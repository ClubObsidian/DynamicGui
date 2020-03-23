package com.clubobsidian.dynamicgui.manager.material;

import java.util.List;

import com.google.inject.Inject;

public abstract class MaterialManager {

	@Inject
	private static MaterialManager instance;
	
	public static MaterialManager get()
	{
		return instance;
	}
	
	public abstract List<String> getMaterials();
	public abstract String normalizeMaterial(String material);

}