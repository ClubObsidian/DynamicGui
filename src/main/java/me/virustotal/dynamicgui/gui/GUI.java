package me.virustotal.dynamicgui.gui;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.FunctionApi;
import me.virustotal.dynamicgui.objects.CLocation;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.objects.ModeEnum;
import me.virustotal.dynamicgui.objects.SoundWrapper;

import org.apache.commons.lang3.SerializationUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6294818826223305057L;
	private ArrayList<Slot> slots = new ArrayList<Slot>();
	private int rows;
	private String name;
	private String title;
	private String permission;
	private String pMessage;
	private Boolean close;
	private ModeEnum modeEnum;
	private List<CLocation> locs = new ArrayList<CLocation>();
	private List<SoundWrapper> openingSounds = new ArrayList<SoundWrapper>();
	private List<Integer> npcIds;
	
	public GUI(String name, String title, int rows,String permission, String pMessage, Boolean close,ModeEnum modeEnum, List<Integer> npcIds, ArrayList<Slot> slots, ArrayList<CLocation> locs, ArrayList<SoundWrapper> openingSounds)
	{
		this.name = name;
		this.title = ChatColor.translateAlternateColorCodes('&', title);
		if(this.title.length() > 32)
			this.title = this.title.substring(0,31);
		this.rows = rows;
		this.slots = slots;
		this.permission = permission;
		this.pMessage = pMessage;
		this.close = close;
		this.modeEnum = modeEnum;
		this.npcIds = npcIds;
		this.locs = locs;
		this.openingSounds = openingSounds;
	}
	
	public GUI(GUI gui) 
	{
		this(gui.getName(),gui.getTitle(),gui.getRows(),gui.getPermission(),gui.getpMessage(),gui.getClose(),gui.getModeEnum(),gui.getNpcIds(),gui.getSlots(),gui.getLocs(), gui.getOpeningSounds());
	}

	public void buildInventory(Player player)
	{	
		Inventory inv = Bukkit.createInventory(null, this.rows * 9, this.title);
		player.openInventory(inv);
		for(int j = 0; j < this.slots.size(); j++)
		{
			Slot slot = this.slots.get(j);
			if(slot != null)
			{
				slot.setGUI(this);
				if(slot.getPermission() != null)
				{
					if(player.hasPermission(slot.getPermission()))
					{
						boolean run = true;
						ItemStack item = slot.buildItemStack(player);
						if(this.modeEnum == ModeEnum.ADD)
						{
							inv.addItem(item);
						}
						else
						{
							inv.setItem(j, item);
						}
						for(Function loadFunction : slot.getLoadFunctions())
						{
							Function func = null;
							try 
							{
								func = FunctionApi.getFunctionByName(loadFunction.getName()).getClass().getConstructor(String.class).newInstance(loadFunction.getName());
								func.setOwner(slot);
							} 
							catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException| NoSuchMethodException | SecurityException e) 
							{
								e.printStackTrace();
							}
							if(func != null)
							{
								func.setData(loadFunction.getData());
								if(!func.function(player))
								{
									run = false;
								}
							}
							else
							{
								DynamicGUI.getInstance().getLogger().log(Level.SEVERE, "Load function " + loadFunction.getName() + " does not exist, will not load in slot.");
								run = false;
							}
							if(!run)
							{
								ArrayList<Function> failFunctions = slot.getFailLoadFunctions(func.getName());
								if(failFunctions != null)
								{
									for(Function fail : failFunctions)
									{
										if(FunctionApi.getFunctionByName(fail.getName()) == null)
											continue;
										try 
										{
											Function failFunction = FunctionApi.getFunctionByName(fail.getName()).getClass().getConstructor(String.class).newInstance(fail.getName());
											failFunction.setData(fail.getData());
											failFunction.setOwner(slot);
											boolean failResult = failFunction.function(player);
											if(!failResult)
											{
												break;
											}
										} 
										catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) 
										{
											e1.printStackTrace();
											break;
										}

									}
								}
								break;
							}
						}
					}
				}
				else
				{
					boolean run = true;
					ItemStack item = slot.buildItemStack(player);
					if(this.modeEnum == ModeEnum.ADD)
					{
						inv.addItem(item);
					}
					else
					{
						inv.setItem(j, item);
					}
					for(Function loadFunction : slot.getLoadFunctions())
					{	
						Function func = null;
						try 
						{
							func =  FunctionApi.getFunctionByName(loadFunction.getName()).getClass().getConstructor(String.class).newInstance(loadFunction.getName());
						} 
						catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException| NoSuchMethodException | SecurityException e) 
						{
							DynamicGUI.getInstance().getLogger().log(Level.SEVERE, loadFunction.getName() + " does not exist!");
							e.printStackTrace();
						}
						if(func != null)
						{
							func.setData(loadFunction.getData());
							func.setOwner(slot);
							if(!func.function(player))
							{
								run = false;
							}
						}
						else
						{
							DynamicGUI.getInstance().getLogger().log(Level.SEVERE, "Load function " + loadFunction.getName() + " does not exist, will not load in slot.");
							run = false;
						}
						if(!run)
						{
							ArrayList<Function> failFunctions = slot.getFailLoadFunctions(func.getName());
							if(failFunctions != null)
							{
								for(Function fail : failFunctions)
								{
									if(FunctionApi.getFunctionByName(fail.getName()) == null)
										continue;
									try 
									{
										Function failFunction = FunctionApi.getFunctionByName(fail.getName()).getClass().getConstructor(String.class).newInstance(fail.getName());
										failFunction.setData(fail.getData());
										failFunction.setOwner(slot);
										boolean failResult = failFunction.function(player);
										if(!failResult)
										{
											break;
										}
									} 
									catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) 
									{
										e1.printStackTrace();
										break;
									}

								}
							}
							break;
						}
					}
				}
			}
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getPermission()
	{
		return this.permission;
	}
	
	public String getpMessage()
	{
		return this.pMessage;
	}
	
	public int getRows()
	{
		return this.rows;
	}
	
	public ArrayList<Slot> getSlots()
	{
		return this.slots;
	}
	
	public Boolean getClose()
	{
		return this.close;
	}
	
	public List<Integer> getNpcIds()
	{
		return this.npcIds;
	}
	
	public List<CLocation> getLocs()
	{
		return this.locs;
	}
	
	public List<SoundWrapper> getOpeningSounds()
	{
		return this.openingSounds;
	}
	
	public ModeEnum getModeEnum()
	{
		return this.modeEnum;
	}
	
	public GUI clone()
	{
		return SerializationUtils.clone(this);
	}
}