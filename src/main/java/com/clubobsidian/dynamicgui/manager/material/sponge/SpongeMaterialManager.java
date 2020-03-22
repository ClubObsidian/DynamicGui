package com.clubobsidian.dynamicgui.manager.material.sponge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.item.ItemTypes;

import com.clubobsidian.dynamicgui.manager.material.MaterialManager;

public class SpongeMaterialManager extends MaterialManager {

	private List<String> materials;
	public SpongeMaterialManager()
	{
		this.loadMaterials();
	}
	
	private void loadMaterials()
	{
		this.materials = new ArrayList<>();
		for(Field field : ItemTypes.class.getDeclaredFields())
		{
			this.materials.add(field.getName());
		}
	}
	
	@Override
	public List<String> getMaterials() 
	{
		return this.materials;
	}

	@Override
	public String normalizeMaterial(String material) 
	{
		if(material == null)
			return null;
		
		return material.toUpperCase();
	}
}