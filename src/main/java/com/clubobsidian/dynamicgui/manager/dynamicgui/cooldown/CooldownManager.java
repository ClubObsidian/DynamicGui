package com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class CooldownManager {

	private static CooldownManager instance;
	
	public static CooldownManager get()
	{
		if(instance == null)
		{
			instance = new CooldownManager();
		}
		
		return instance;
	}
	
	private Map<UUID, Map<String, Cooldown>> cooldowns;
	private Configuration cooldownConfig;
	private CooldownManager()
	{
		this.cooldowns = new ConcurrentHashMap<>();
		this.cooldownConfig = this.loadConfig();
		this.scheduleCooldownUpdate();
	}
	
	private Configuration loadConfig()
	{
		File dataFolder = DynamicGui.get().getPlugin().getDataFolder();
		File cooldownsFile = new File(dataFolder, "cooldowns.yml");
		Configuration config = Configuration.load(cooldownsFile);
		for(String uuidStr : config.getKeys())
		{
			ConfigurationSection section = config.getConfigurationSection(uuidStr);
			if(section.isEmpty())
			{
				config.set(uuidStr, null);
				continue;
			}
			
			Map<String, Cooldown> cooldownMap = new ConcurrentHashMap<>();
			for(String cooldownName : section.getKeys())
			{
				Long time = section.getLong(cooldownName + ".time");
				Long cooldown = section.getLong(cooldownName + ".cooldown");
				Cooldown cooldownObj = new Cooldown(cooldownName, time, cooldown);
				if(this.getRemainingCooldown(cooldownObj) != -1L)
				{
					cooldownMap.put(cooldownName, cooldownObj);
				}
			}
			
			if(cooldownMap.size() > 0)
			{
				UUID uuid = UUID.fromString(uuidStr);
				this.cooldowns.put(uuid, cooldownMap);
			}
		}
		
		config.save();
		return config;
	}
	
	public Long getRemainingCooldown(PlayerWrapper<?> playerWrapper, String name)
	{
		return this.getRemainingCooldown(playerWrapper.getUniqueId(), name);
	}
	
	public Long getRemainingCooldown(UUID uuid, String name)
	{
		Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
		if(cooldownMap == null)
		{
			return -1L;
		}
		
		Cooldown cooldown = cooldownMap.get(name);
		return this.getRemainingCooldown(cooldown);
	}
	
	public Long getRemainingCooldown(Cooldown cooldown)
	{
		long currentTime = System.currentTimeMillis();
		Long cooldownTime = cooldown.getTime();
		Long cooldownAmount = cooldown.getCooldown();
		
		if((currentTime - cooldownTime) >= cooldownAmount)
		{
			return -1L;
		}
		else
		{
			return cooldownAmount - (currentTime - cooldownTime);
		}
	}
	
	public boolean isOnCooldown(PlayerWrapper<?> playerWrapper, String name)
	{
		return this.isOnCooldown(playerWrapper.getUniqueId(), name);
	}
	
	public boolean isOnCooldown(UUID uuid, String name)
	{
		return this.getRemainingCooldown(uuid, name) != -1L;
	}
	
	public Collection<Cooldown> getCooldown(PlayerWrapper<?> playerWrapper)
	{
		UUID uuid = playerWrapper.getUniqueId();
		return this.getCooldowns(uuid);
	}
	
	public Collection<Cooldown> getCooldowns(UUID uuid)
	{
		Map<String, Cooldown> cooldowns = this.cooldowns.get(uuid);
		if(cooldowns == null)
		{
			return null;
		}
		
		return cooldowns.values();
	}
	
	public Cooldown createCooldown(PlayerWrapper<?> playerWrapper, String name, Long cooldownTime)
	{
		UUID uuid = playerWrapper.getUniqueId();
		return this.createCooldown(uuid, name, cooldownTime);
	}
	
	public Cooldown createCooldown(UUID uuid, String name, Long cooldownTime)
	{
		Long cooldownRemaining = this.getRemainingCooldown(uuid, name);
		if(cooldownRemaining == -1L)
		{
			Long currentTime = System.currentTimeMillis();
			Cooldown cooldown = new Cooldown(name, currentTime, cooldownTime);
			Map<String, Cooldown> cooldownMap = this.cooldowns.get(uuid);
			if(cooldownMap == null)
			{
				cooldownMap = new ConcurrentHashMap<>();
				this.cooldowns.put(uuid, cooldownMap);
			}
			
			cooldownMap.put(name, cooldown);
			return cooldown;
		}
		
		return null;
	}
	
	public void shutdown()
	{
		Iterator<Entry<UUID, Map<String, Cooldown>>> it = this.cooldowns.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<UUID, Map<String, Cooldown>> next = it.next();
			UUID uuid = next.getKey();
			String uuidStr = uuid.toString();
			Map<String, Cooldown> cooldownMap = next.getValue();
			cooldownMap.forEach((cooldownName, cooldownObj) ->
			{
				Long time = cooldownObj.getTime();
				Long cooldown = cooldownObj.getCooldown();
				ConfigurationSection cooldownSection = this.cooldownConfig.getConfigurationSection(uuidStr + "." + cooldownName);
				cooldownSection.set("time"  , time);
				cooldownSection.set("cooldown"  , cooldown);
			});
		}
		
		this.cooldownConfig.save();
	}
	
	private void updateCache()
	{
		Iterator<Entry<UUID, Map<String, Cooldown>>> it = this.cooldowns.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<UUID, Map<String, Cooldown>> next = it.next();
			Map<String, Cooldown> cooldownMap = next.getValue();
			Iterator<Entry<String, Cooldown>> cooldownIt = cooldownMap.entrySet().iterator();
			while(cooldownIt.hasNext())
			{
				Entry<String, Cooldown> cooldownNext = cooldownIt.next();
				Cooldown cooldown = cooldownNext.getValue();
				Long cooldownRemaining = this.getRemainingCooldown(cooldown);
				if(cooldownRemaining == -1L)
				{
					cooldownIt.remove();
				}
			}
		}
	}
	
	private void scheduleCooldownUpdate()
	{
		DynamicGui dynamicGui = DynamicGui.get();
		DynamicGuiPlugin plugin = dynamicGui.getPlugin();
		FakeServer server = dynamicGui.getServer();
		server.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
		{
			this.updateCache();
		}, 20L, 20L);
	}
}