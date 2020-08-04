package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.HashMap;
import java.util.Map;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.event.DynamicGuiReloadEvent;
import com.clubobsidian.trident.EventHandler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class MiniMessageManager {

	public static MiniMessageManager instance;

	public static MiniMessageManager get()
	{
		if(instance == null)
		{
			instance = new MiniMessageManager();
		}
		return instance;
	}
	
	private Map<String, String> json;
	private MiniMessage miniMessage;
	private GsonComponentSerializer serializer;
	private MiniMessageManager()
	{
		this.json = new HashMap<>();
		this.miniMessage = MiniMessage
				.builder()
				.markdown()
				.build();
		this.serializer = GsonComponentSerializer.builder().build();
		DynamicGui.get().getEventBus().registerEvents(this);
	}
	
	public String toJson(String data)
	{
		String cached = this.json.get(data);
		if(cached == null)
		{
			Component component = this.miniMessage.deserialize(data);
			cached = this.serializer.serialize(component);
			this.json.put(data, cached);
		}
		return cached;
	}
	
	@EventHandler
	public void onReload(DynamicGuiReloadEvent event)
	{
		this.json.clear();
	}
}