package com.clubobsidian.dynamicgui.manager.material.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.clubobsidian.dynamicgui.manager.material.MaterialManager;

public class BukkitMaterialManager extends MaterialManager {

	private List<String> materials;
	public BukkitMaterialManager() 
	{
		this.loadMaterials();
	}

	private void loadMaterials()
	{
		this.materials = new ArrayList<>();
		for(Material material : Material.values())
		{
			this.materials.add(material.name());
		}
	}
	
	@Override
	public List<String> getMaterials() 
	{
		return this.materials;
	}

}
