/*
   Copyright 2020 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.inject.module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

public class PluginModule implements Module {

	private Class<? extends EntityManager> entityClass;
	private Class<? extends InventoryManager> inventoryClass;
	private Class<? extends ItemStackManager> itemStackClass;
	private Class<? extends MaterialManager> materialClass;
	private Class<? extends LocationManager> locationClass;
	private DynamicGuiPlugin plugin;
	private FakeServer fakeServer;
	private LoggerWrapper<?> loggerWrapper;
	
	public PluginModule setEntity(Class<? extends EntityManager> clazz)
	{
		this.entityClass = clazz;
		return this;
	}
	
	public PluginModule setInventory(Class<? extends InventoryManager> clazz)
	{
		this.inventoryClass = clazz;
		return this;
	}
	
	public PluginModule setItemStack(Class <? extends ItemStackManager> clazz)
	{
		this.itemStackClass = clazz;
		return this;
	}
	
	public PluginModule setMaterial(Class <? extends MaterialManager> clazz)
	{
		this.materialClass = clazz;
		return this;
	}
	
	public PluginModule setLocation(Class <? extends LocationManager> clazz)
	{
		this.locationClass = clazz;
		return this;
	}
	
	public PluginModule setPlugin(DynamicGuiPlugin plugin)
	{
		this.plugin = plugin;
		return this;
	}
	
	public PluginModule setServer(FakeServer server)
	{
		this.fakeServer = server;
		return this;
	}
	
	public PluginModule setLogger(LoggerWrapper<?> logger)
	{
		this.loggerWrapper = logger;
		return this;
	}
	
	@Override
	public void configure(Binder binder) 
	{
		binder.bind(EntityManager.class).to(this.entityClass);
		binder.bind(InventoryManager.class).to(this.inventoryClass);
		binder.bind(ItemStackManager.class).to(this.itemStackClass);
		binder.bind(MaterialManager.class).to(this.materialClass);
		binder.bind(LocationManager.class).to(this.locationClass);
		binder.bind(DynamicGuiPlugin.class).toInstance(this.plugin);
		binder.bind(FakeServer.class).toInstance(this.fakeServer);
		binder.bind(new TypeLiteral<LoggerWrapper<?>>(){}).toInstance(this.loggerWrapper);
		
		binder.requestStaticInjection(EntityManager.class);
		binder.requestStaticInjection(InventoryManager.class);
		binder.requestStaticInjection(ItemStackManager.class);
		binder.requestStaticInjection(MaterialManager.class);
		binder.requestStaticInjection(LocationManager.class);
		binder.requestStaticInjection(DynamicGui.class);
	}
	
	public boolean bootstrap()
	{
		Guice.createInjector(this);
		DynamicGui dynamicGui = DynamicGui.get();
		Class<?> dynamicGuiClass = dynamicGui.getClass();
		try 
		{
			Method init = dynamicGuiClass.getDeclaredMethod("init");
			init.setAccessible(true);
			init.invoke(dynamicGui);
			return true;
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
}