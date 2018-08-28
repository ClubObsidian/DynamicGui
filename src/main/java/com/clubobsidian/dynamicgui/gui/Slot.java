package com.clubobsidian.dynamicgui.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.objects.EnchantmentWrapper;

public class Slot implements Serializable, FunctionOwner {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2366997214615469494L;
	private String icon;
	private String name;
	private String nbt;
	private short data;
	private int index;
	private List<Function> loadFunctions;
	private Map<String, List<Function>> failLoadFunctions;
	//All clicks
	private List<Function> functions;
	private Map<String, List<Function>> failFunctions;
	//Left click
	private List<Function> leftClickFunctions;
	private Map<String, List<Function>> leftClickFailFunctions;
	//Right click
	private List<Function> rightClickFunctions;
	private Map<String, List<Function>> rightClickFailFunctions;
	//Middle click
	private List<Function> middleClickFunctions;
	private Map<String, List<Function>> middleClickFailFunctions;
	private List<String> lore;
	private List<EnchantmentWrapper> enchants;
	private Boolean close;
	private int amount;
	private ItemStackWrapper<?> itemStack;
	private Gui owner;

	public Slot(String icon, String name, String nbt, short data, Boolean close, List<String> lore, List<EnchantmentWrapper> enchants, int index, List<Function> functions, List<Function> leftClickFunctions, Map<String, List<Function>> leftClickFailFunctions, List<Function> rightClickFunctions, Map<String, List<Function>> rightClickFailFunctions, List<Function> middleClickFunctions, Map<String, List<Function>> middleClickFailFunctions, Map<String, List<Function>> failFunctions, List<Function> loadFunctions, Map<String, List<Function>> failLoadFunctions)
	{
		this(icon, name, nbt, data, close, lore, enchants, index, functions, failFunctions, leftClickFunctions, leftClickFailFunctions, rightClickFunctions, rightClickFailFunctions, middleClickFunctions, middleClickFailFunctions, loadFunctions, failLoadFunctions, 1);
		//, ArrayList<Function> leftClickFunctions, HashMap<String, ArrayList<Function>> leftClickFailFunctions, ArrayList<Function> rightClickFunctions, HashMap<String, ArrayList<Function>> rightClickFailFunctions, ArrayList<Function> middleClickFunctions, HashMap<String, ArrayList<Function>> middleClickFailFunctions, 
	}
	
	public Slot(String icon, String name, String nbt, short data, Boolean close, List<String> lore, List<EnchantmentWrapper> enchants, int index, List<Function> functions, Map<String, List<Function>> failFunctions, List<Function> leftClickFunctions, Map<String, List<Function>> leftClickFailFunctions, List<Function> rightClickFunctions, Map<String, List<Function>> rightClickFailFunctions, List<Function> middleClickFunctions, Map<String, List<Function>> middleClickFailFunctions, List<Function> loadFunctions, Map<String, List<Function>> failLoadFunctions, int amount)
	{
		this.icon = icon;
		this.failFunctions = failFunctions;
		this.loadFunctions = loadFunctions;
		this.failLoadFunctions = failLoadFunctions;
		this.functions = functions;
		this.leftClickFunctions = leftClickFunctions;
		this.leftClickFailFunctions = leftClickFailFunctions;
		this.rightClickFunctions = rightClickFunctions;
		this.rightClickFailFunctions = rightClickFailFunctions;
		this.middleClickFunctions = middleClickFunctions;
		this.middleClickFailFunctions = middleClickFailFunctions;
		this.data = data;
		this.name = name;
		this.nbt = nbt;
		this.lore = lore;
		this.enchants = enchants;
		this.close = close;
		this.index = index;
		this.amount = amount;
	}
	
	
	public Slot(ItemStackWrapper<?> item, int index, boolean close)
	{
		this.icon = item.getType().toString();
		this.functions = new ArrayList<Function>();
		this.loadFunctions = new ArrayList<Function>();
		this.copyDataFromItemStack(item);
		this.close = close;
		this.index = index;
	}

	private void copyDataFromItemStack(ItemStackWrapper<?> item)
	{
		this.amount = item.getAmount();
		this.data = item.getDurability();
		this.name = item.getName();
		this.enchants = new ArrayList<EnchantmentWrapper>();
		List<EnchantmentWrapper> enchants = item.getEnchants();
		for(EnchantmentWrapper ench : enchants)
		{
			this.enchants.add(ench);
		}
		this.lore = item.getLore();
	}

	public String getIcon()
	{
		return this.icon;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public short getData()
	{
		return this.data;
	}
	
	public List<Function> getFunctions()
	{
		return this.functions;
	}
	
	public List<Function> getFailFunctions(String key)
	{
		return this.failFunctions.get(key);
	}
	
	public List<Function> getLeftClickFunctions()
	{
		return this.leftClickFunctions;
	}
	
	public List<Function> getLeftClickFailFunctions(String key)
	{
		return this.leftClickFailFunctions.get(key);
	}
	
	public List<Function> getRightClickFunctions()
	{
		return this.rightClickFunctions;
	}
	
	public List<Function> getRightClickFailFunctions(String key)
	{
		return this.rightClickFailFunctions.get(key);
	}
	
	public List<Function> getMiddleClickFunctions()
	{
		return this.middleClickFunctions;
	}
	
	public List<Function> getMiddleClickFailFunctions(String key)
	{
		return this.middleClickFailFunctions.get(key);
	}
	
	public List<Function> getFailLoadFunctions(String key)
	{
		return this.failLoadFunctions.get(key);
	}
	
	public List<Function> getLoadFunctions()
	{
		return this.loadFunctions;
	}
	
	public Boolean getClose()
	{
		return this.close;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public ItemStackWrapper<?> buildItemStack(PlayerWrapper<?> player)
	{
		ItemStackWrapper<?> item = ItemStackManager.get().createItemStackWrapper(this.icon, this.getAmount());
		
		if(this.getData() != 0)
			item.setDurability(this.getData());
		
		if(this.name != null)
		{
			String newName = this.name;
			newName = ReplacerManager.get().replace(newName, player);
			item.setName(newName);
		}
		
		if(this.lore != null)
		{
			List<String> newLore = new ArrayList<>();
			
			for(String newString : this.lore)
				newLore.add(newString);
			
			for(int i = 0; i < newLore.size(); i++)
			{
				newLore.set(i, ReplacerManager.get().replace(newLore.get(i), player));
			}
			
		
			item.setLore(newLore);
		}
		
		if(this.enchants != null)
		{
			for(EnchantmentWrapper ench : this.enchants)
				item.addEnchant(ench);
		}
		
		
		this.itemStack = item;
		return item;
	}
	
	public ItemStackWrapper<?> getItemStack()
	{
		return this.itemStack;
	}
	
	public void setOwner(Gui gui)
	{
		this.owner = gui;
	}
	
	public Gui getOwner()
	{
		return this.owner;
	}
}