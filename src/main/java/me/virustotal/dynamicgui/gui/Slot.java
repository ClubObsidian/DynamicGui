package me.virustotal.dynamicgui.gui;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.objects.MyEnchantment;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Slot implements Serializable {
	
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
	private HashMap<String, ArrayList<Function>> failLoadFunctions;
	//All clicks
	private List<Function> functions;
	private HashMap<String, ArrayList<Function>> failFunctions;
	//Left click
	private List<Function> leftClickFunctions;
	private HashMap<String, ArrayList<Function>> leftClickFailFunctions;
	//Right click
	private List<Function> rightClickFunctions;
	private HashMap<String, ArrayList<Function>> rightClickFailFunctions;
	//Middle click
	private List<Function> middleClickFunctions;
	private HashMap<String, ArrayList<Function>> middleClickFailFunctions;
	private List<String> lore;
	private List<MyEnchantment> enchants;
	private String permission;
	private String pMessage;
	private Boolean close;
	private int amount;
	private UUID uuid;
	private ItemStack itemStack;
	private GUI gui;

	public Slot(String icon, String name, String nbt, short data, Boolean close, List<String> lore, List<MyEnchantment> enchants,String permission, String pMessage, int index, ArrayList<Function> functions, ArrayList<Function> leftClickFunctions, HashMap<String, ArrayList<Function>> leftClickFailFunctions, ArrayList<Function> rightClickFunctions, HashMap<String, ArrayList<Function>> rightClickFailFunctions, ArrayList<Function> middleClickFunctions, HashMap<String, ArrayList<Function>> middleClickFailFunctions, HashMap<String, ArrayList<Function>> failFunctions, ArrayList<Function> loadFunctions, HashMap<String, ArrayList<Function>> failLoadFunctions)
	{
		this(icon, name, nbt, data, close, lore, enchants, permission, pMessage, index, functions, failFunctions, leftClickFunctions, leftClickFailFunctions, rightClickFunctions, rightClickFailFunctions, middleClickFunctions, middleClickFailFunctions, loadFunctions, failLoadFunctions, 1);
		//, ArrayList<Function> leftClickFunctions, HashMap<String, ArrayList<Function>> leftClickFailFunctions, ArrayList<Function> rightClickFunctions, HashMap<String, ArrayList<Function>> rightClickFailFunctions, ArrayList<Function> middleClickFunctions, HashMap<String, ArrayList<Function>> middleClickFailFunctions, 
	}
	
	public Slot(String icon, String name, String nbt, short data, Boolean close, List<String> lore, List<MyEnchantment> enchants,String permission, String pMessage, int index, ArrayList<Function> functions, HashMap<String, ArrayList<Function>> failFunctions, ArrayList<Function> leftClickFunctions, HashMap<String, ArrayList<Function>> leftClickFailFunctions, ArrayList<Function> rightClickFunctions, HashMap<String, ArrayList<Function>> rightClickFailFunctions, ArrayList<Function> middleClickFunctions, HashMap<String, ArrayList<Function>> middleClickFailFunctions, ArrayList<Function> loadFunctions, HashMap<String, ArrayList<Function>> failLoadFunctions, int amount)
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
		this.permission = permission;
		this.pMessage = pMessage;
		this.close = close;
		this.index = index;
		this.amount = amount;
	}
	
	
	public Slot(ItemStack item, int index, boolean close)
	{
		this.icon = item.getType().toString();
		this.functions = new ArrayList<Function>();
		this.loadFunctions = new ArrayList<Function>();
		this.data = item.getDurability();
		this.setMeta(item);
		this.close = close;
		this.index = index;
		this.amount = item.getAmount();
	}



	private void setMeta(ItemStack item)
	{
		System.out.println(item);
		this.enchants = new ArrayList<MyEnchantment>();
		if(item.hasItemMeta())
		{

			System.out.println(item.getType());

			if(item.getItemMeta().hasDisplayName())
				this.name = item.getItemMeta().getDisplayName();
			if(item.getItemMeta().hasEnchants())
			{
				Map<Enchantment, Integer> enchants = item.getItemMeta().getEnchants();
				for(Enchantment ench : enchants.keySet())
				{
					this.enchants.add(new MyEnchantment(ench, item.getItemMeta().getEnchantLevel(ench)));
				}	
			}
			if(item.getItemMeta().hasLore())
			{
				this.lore = item.getItemMeta().getLore();
			}
		}
	}
	
	public String getIcon()
	{
		return this.icon;
	}
	
	public int getIndex()
	{
		return this.index;
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
	
	public String getPermission()
	{
		return this.permission;
	}
	
	public String pMessage()
	{
		return this.permission;
	}
	
	public String getpMessage()
	{
		return this.pMessage;
	}
	
	public Boolean getClose()
	{
		return this.close;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public ItemStack buildItemStack(Player player)
	{
		ItemStack item = new ItemStack(Material.matchMaterial(this.icon), this.getAmount());
		try 
		{
			/*if(this.nbt != null && !this.nbt.equals(""))
			{
				NBTItem nbtItem = new NBTItem(item);
				
				item = ItemUtil.setNBTForItemStack(item, ReplacerAPI.replace(this.nbt, player));
			} */ 
			//TODO
			UUID uuid = UUID.randomUUID();
			NBTItem nbtItem = new NBTItem(item);
			nbtItem.setString(DynamicGUI.TAG, uuid.toString());
			item = nbtItem.getItem();
			this.uuid = uuid;
		}
		catch (SecurityException | IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
		
		if(this.getData() != 0)
			item.setDurability(this.getData());
		
		ItemMeta itemMeta = item.getItemMeta();
		
		if(this.name != null)
		{
			String newName = this.name;
			
			newName = ReplacerAPI.replace(newName, player);
			
			if(DynamicGUI.getPlaceholderAPI())
			{
				itemMeta.setDisplayName(PlaceholderAPI.setPlaceholders(player, newName));
			}
			else 
			{
				itemMeta.setDisplayName(newName);
			}
		}
		
		if(this.lore != null)
		{
			List<String> newLore = new ArrayList<String>();
			
			for(String newString : this.lore)
				newLore.add(newString);
			
			for(int i = 0; i < newLore.size(); i++)
			{
				newLore.set(i, ReplacerAPI.replace(newLore.get(i), player));
			}
			
			if(DynamicGUI.getPlaceholderAPI())
			{
				for(int i = 0; i < newLore.size(); i++)
				{
					newLore.set(i, PlaceholderAPI.setPlaceholders(player, newLore.get(i)));
				} 
			}
			itemMeta.setLore(newLore);
		}
		
		if(this.enchants != null)
		{
			for(MyEnchantment ench : this.enchants)
				itemMeta.addEnchant(ench.getEnchant(), ench.getLevel(), false);
		}
		
		item.setItemMeta(itemMeta);
		this.itemStack = item;
		return item;
	}
	
	public ItemStack getItemStack()
	{
		return this.itemStack;
	}
	
	public UUID getUUID()
	{
		return this.uuid;
	}
	
	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}
	
	public GUI getGUI()
	{
		return this.gui;
	}
}