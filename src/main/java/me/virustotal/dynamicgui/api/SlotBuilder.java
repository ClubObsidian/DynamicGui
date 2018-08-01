package me.virustotal.dynamicgui.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.objects.MyEnchantment;

public class SlotBuilder {
	
	private String icon;
	private String name;
	private String nbt;
	private short data;
	private Boolean close;
	private List<String> lore;
	private List<MyEnchantment> enchants;
	private String permission;
	private String pMessage;
	private int index;
	
	private List<Function> functions = new ArrayList<Function>();
	private Map<String, List<Function>> failFunctions = new HashMap<String, List<Function>>();
	
	private List<Function> leftClickFunctions = new ArrayList<Function>();
	private Map<String, List<Function>> leftClickFailFunctions = new HashMap<String, List<Function>>();
	
	private List<Function> rightClickFunctions = new ArrayList<Function>();
	private Map<String, List<Function>> rightClickFailFunctions = new HashMap<String, List<Function>>();
	
	private List<Function> middleClickFunctions = new ArrayList<Function>();
	private Map<String, List<Function>> middleClickFailFunctions = new HashMap<String, List<Function>>();
	
	private List<Function> loadFunctions = new ArrayList<Function>();
	private Map<String, List<Function>> failLoadFunctions = new HashMap<String, List<Function>>();
	private int amount;
	
	public SlotBuilder setIcon(Material icon)
	{
		this.icon = icon.name();
		return this;
	}
	
	public SlotBuilder setIcon(String icon)
    {
    	this.icon = icon;
    	return this;
    }
	
	public SlotBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public SlotBuilder setNBT(String nbt)
	{
		this.nbt = nbt;
		return this;
	}
	
	public SlotBuilder setData(int data)
	{
		this.setData((short) data);
		return this;
	}
	
	public SlotBuilder setData(short data)
	{
		this.data = data;
		return this;
	}
	
	public SlotBuilder setClose(Boolean close)
	{
		this.close = close;
		return this;
	}
	
	public SlotBuilder setIndex(int index)
	{
		this.index = index;
		return this;
	}
	
	public SlotBuilder setAmount(int amount)
	{
		this.amount = amount;
		return this;
	}
	
	public SlotBuilder addFunction(Function func)
	{
		this.functions.add(func);
		return this;
	}
	
	public SlotBuilder addFailFunction(String key, Function func)
	{
		if(this.failFunctions.containsKey(key))
		{
			if(this.failFunctions.get(key) != null)
			{
				this.failFunctions.get(key).add(func);
				return this;
			}
		}
		
		ArrayList<Function> funcs = new ArrayList<Function>();
		funcs.add(func);
		this.failFunctions.put(key, funcs);
		return this;
	}
	
	public SlotBuilder addLeftClickFunction(Function func)
	{
		this.leftClickFunctions.add(func);
		return this;
	}
	
	public SlotBuilder addLeftClickFailFunction(String key, Function func)
	{
		if(this.leftClickFailFunctions.containsKey(key))
		{
			if(this.leftClickFailFunctions.get(key) != null)
			{
				this.leftClickFailFunctions.get(key).add(func);
				return this;
			}
		}
		
		ArrayList<Function> funcs = new ArrayList<Function>();
		funcs.add(func);
		this.leftClickFailFunctions.put(key, funcs);
		return this;
	}
	
	public SlotBuilder addRightClickFunction(Function func)
	{
		this.rightClickFunctions.add(func);
		return this;
	}
	
	public SlotBuilder addRightClickFailFunction(String key, Function func)
	{
		if(this.rightClickFailFunctions.containsKey(key))
		{
			if(this.rightClickFailFunctions.get(key) != null)
			{
				this.rightClickFailFunctions.get(key).add(func);
				return this;
			}
		}
		
		ArrayList<Function> funcs = new ArrayList<Function>();
		funcs.add(func);
		this.rightClickFailFunctions.put(key, funcs);
		return this;
	}
	
	public SlotBuilder addMiddleClickFunction(Function func)
	{
		this.middleClickFunctions.add(func);
		return this;
	}
	
	public SlotBuilder addMiddleClickFailFunction(String key, Function func)
	{
		if(this.middleClickFailFunctions.containsKey(key))
		{
			if(this.middleClickFailFunctions.get(key) != null)
			{
				this.middleClickFailFunctions.get(key).add(func);
				return this;
			}
		}
		
		ArrayList<Function> funcs = new ArrayList<Function>();
		funcs.add(func);
		this.middleClickFailFunctions.put(key, funcs);
		return this;
	}
	
	public SlotBuilder addFailLoadFunction(String key, Function func)
	{
		if(this.failLoadFunctions.containsKey(key))
		{
			if(this.failLoadFunctions.get(key) != null)
			{
				this.failLoadFunctions.get(key).add(func);
				return this;
			}
		}
		
		ArrayList<Function> funcs = new ArrayList<Function>();
		funcs.add(func);
		this.failLoadFunctions.put(key, funcs);
		return this;
	}
	
	public SlotBuilder addLoadFunction(Function func)
	{
		this.loadFunctions.add(func);
		return this;
	}
	
	public SlotBuilder addLore(String lore)
	{
		if(this.lore == null)
		{
			this.lore = new ArrayList<String>();
			this.lore.add(lore);
		}
		else
		{
			this.lore.add(lore);
		}
		return this;
	}
	
	public SlotBuilder addLore(ArrayList<String> lore)
	{
		if(this.lore == null)
		{
			this.lore = lore;
		}
		else
		{
			for(String str : lore)
			{
				this.lore.add(str);
			}
		}
		return this;
	}
	
	public Slot build()
	{
		return new Slot(this.icon, this.name, this.nbt, this.data, this.close, this.lore, this.enchants, this.permission, this.pMessage, this.index, this.functions, this.failFunctions, this.leftClickFunctions, this.leftClickFailFunctions, this.rightClickFunctions, this.rightClickFailFunctions, this.middleClickFunctions, this.middleClickFailFunctions, this.loadFunctions, this.failLoadFunctions, this.amount);
	}

}