package com.clubobsidian.dynamicgui.api;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.objects.ModeEnum;
import com.clubobsidian.dynamicgui.objects.SoundWrapper;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

public class GuiBuilder  {
	
	private String name;
	private String title;
	private int rows;
	private String permission;
	private String pMessage;
	private Boolean close;
	private ModeEnum modeEnum;
	private List<Integer> npcIds = new ArrayList<Integer>();
	private List<Slot> slots = new ArrayList<Slot>();
	private List<LocationWrapper<?>> locs = new ArrayList<>();
	private List<SoundWrapper> openingSounds = new ArrayList<>();
	
	public GuiBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public GuiBuilder setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public GuiBuilder setRows(int rows)
	{
		this.rows = rows;
		return this;
	}
	
	public GuiBuilder setPermission(String permission)
	{
		this.permission = permission;
		return this;
	}
	
	public GuiBuilder setPermissionMessage(String pMessage)
	{
		this.pMessage = pMessage;
		return this;
	}
	
	public GuiBuilder setClose(Boolean close)
	{
		this.close = close;
		return this;
	}
	public GuiBuilder setModeEnum(String mode)
	{
		this.setModeEnum(ModeEnum.valueOf(mode));
		return this;
	}
	
	public GuiBuilder setModeEnum(ModeEnum modeEnum)
	{
		this.modeEnum = modeEnum;
		return this;
	}
	
	public GuiBuilder addNpcId(Integer id)
	{
		this.npcIds.add(id);
		return this;
	}
	
	public GuiBuilder addNpcId(Integer[] npcIds)
	{
		for(Integer id : npcIds)
		{
			this.npcIds.add(id);
		}
		return this;
	}
	
	public GuiBuilder addNpcId(ArrayList<Integer> npcIds)
	{
		for(Integer id : npcIds)
		{
			this.npcIds.add(id);
		}
		return this;
	}
	
	public GuiBuilder addSlot(Slot slot)
	{
		this.slots.add(slot);
		return this;
	}
	
	public GuiBuilder addLocation(LocationWrapper<?> loc)
	{
		this.locs.add(loc);
		return this;
	}
	
	public GUI build()
	{
		return new GUI(this.name, this.title, this.rows, this.permission, this.pMessage, this.close, this.modeEnum, this.npcIds, this.slots, this.locs, this.openingSounds);
	}
}