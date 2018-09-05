package com.clubobsidian.dynamicgui.registry.npc.citizens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Entity;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.registry.npc.NPC;
import com.clubobsidian.dynamicgui.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.util.ReflectionUtil;

public class CitizensRegistry implements NPCRegistry 
{
	private Class<?> citizensApiClass;
	private Class<?> npcClass;
	private Method getNPCRegistryMethod;
	private Method getNPCMethod;
	private Method getIdMethod;
	private Object npcRegistry;
	public CitizensRegistry()
	{
		this.citizensApiClass = ReflectionUtil.classForName("net.citizensnpcs.api.CitizensAPI");
		this.npcClass = ReflectionUtil.classForName("net.citizensnpcs.api.npc.NPC");
		this.npcRegistry = this.getNPCRegistry();
		this.getIdMethod = ReflectionUtil.getMethod(this.npcClass, "getId");
		this.getNPCMethod = ReflectionUtil.getMethod(this.npcRegistry.getClass(), "getNPC", Entity.class);
	}
	
	private Object getNPCRegistry()
	{
		this.getNPCRegistryMethod = ReflectionUtil.getMethod(this.citizensApiClass, "getNPCRegistry");
		try 
		{
			return this.getNPCRegistryMethod.invoke(null);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isNPC(EntityWrapper<?> entityWrapper) 
	{
		try 
		{
			return this.getNPCMethod.invoke(this.npcRegistry, entityWrapper.getEntity()) != null;
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public NPC getNPC(EntityWrapper<?> entityWrapper) 
	{
		try 
		{
			Object npc = this.getNPCMethod.invoke(this.npcRegistry, entityWrapper.getEntity());
			int id = (int) this.getIdMethod.invoke(npc);
			return new NPC(entityWrapper, id);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}