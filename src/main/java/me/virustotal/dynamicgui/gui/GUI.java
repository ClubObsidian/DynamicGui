package me.virustotal.dynamicgui.gui;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.FunctionApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.manager.inventory.InventoryManager;
import me.virustotal.dynamicgui.objects.ModeEnum;
import me.virustotal.dynamicgui.objects.SoundWrapper;
import me.virustotal.dynamicgui.util.ChatColor;
import me.virustotal.dynamicgui.world.LocationWrapper;

import org.apache.commons.lang3.SerializationUtils;


public class GUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6294818826223305057L;
	private List<Slot> slots = new ArrayList<>();
	private String name;
	private String title;
	private int rows;
	private String permission;
	private String pMessage;
	private Boolean close;
	private ModeEnum modeEnum;
	private List<LocationWrapper<?>> locations = new ArrayList<>();
	private List<SoundWrapper> openingSounds = new ArrayList<>();
	private List<Integer> npcIds = new ArrayList<>();
	
	public GUI(String name, String title, int rows,String permission, String pMessage, Boolean close, ModeEnum modeEnum, List<Integer> npcIds, List<Slot> slots, List<LocationWrapper<?>> locations, List<SoundWrapper> openingSounds)
	{
		this.name = name;
		this.title = ChatColor.translateAlternateColorCodes(title);
		if(this.title.length() > 32)
			this.title = this.title.substring(0,31);
		this.rows = rows;
		this.slots = slots;
		this.permission = permission;
		this.pMessage = pMessage;
		this.close = close;
		this.modeEnum = modeEnum;
		this.npcIds = npcIds;
		this.locations = locations;
		this.openingSounds = openingSounds;
	}
	
	public GUI(GUI gui) 
	{
		this(gui.getName(),gui.getTitle(),gui.getRows(),gui.getPermission(),gui.getpMessage(),gui.getClose(),gui.getModeEnum(),gui.getNpcIds(),gui.getSlots(),gui.getLocations(), gui.getOpeningSounds());
	}

	public void buildInventory(PlayerWrapper<?> player)
	{	
		Object serverInventory = InventoryManager.get().createInventory(this.rows * 9, this.title);
		InventoryWrapper<?> inv = InventoryManager.get().createInventoryWrapper(serverInventory);
		player.openInventory(inv);
		
		for(int j = 0; j < this.slots.size(); j++)
		{
			Slot slot = this.slots.get(j);
			if(slot != null)
			{
				slot.setOwner(this);
				if(slot.getPermission() != null)
				{
					if(player.hasPermission(slot.getPermission()))
					{
						boolean run = true;
						ItemStackWrapper<?> item = slot.buildItemStack(player);
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
								DynamicGUI.get().getPlugin().getLogger().log(Level.SEVERE, "Load function " + loadFunction.getName() + " does not exist, will not load in slot.");
								run = false;
							}
							if(!run)
							{
								List<Function> failFunctions = slot.getFailLoadFunctions(func.getName());
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
					ItemStackWrapper<?> item = slot.buildItemStack(player);
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
							DynamicGUI.get().getPlugin().getLogger().log(Level.SEVERE, loadFunction.getName() + " does not exist!");
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
							DynamicGUI.get().getPlugin().getLogger().log(Level.SEVERE, "Load function " + loadFunction.getName() + " does not exist, will not load in slot.");
							run = false;
						}
						if(!run)
						{
							List<Function> failFunctions = slot.getFailLoadFunctions(func.getName());
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
	
	public List<Slot> getSlots()
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
	
	public List<LocationWrapper<?>> getLocations()
	{
		return this.locations;
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