/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.registry.npc.citizens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Entity;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.registry.npc.NPC;
import com.clubobsidian.dynamicgui.registry.npc.NPCMeta;
import com.clubobsidian.dynamicgui.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.util.ReflectionUtil;

public class CitizensRegistry implements NPCRegistry 
{
	private static final String PLUGIN_NAME = "Citizens";
	
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
	public String getName()
	{
		return PLUGIN_NAME;
	}
	
	@Override
	public boolean isNPC(EntityWrapper<?> entityWrapper) 
	{
		return this.getNPC(entityWrapper) != null;
	}

	@Override
	public NPC getNPC(EntityWrapper<?> entityWrapper) 
	{
		try 
		{
			Object npc = this.getNPCMethod.invoke(this.npcRegistry, entityWrapper.getEntity());
			if(npc == null)
			{
				return null;
			}
			
			int id = (int) this.getIdMethod.invoke(npc);
			NPCMeta meta = new NPCMeta(id, CitizensRegistry.PLUGIN_NAME);
			return new NPC(entityWrapper, meta);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}