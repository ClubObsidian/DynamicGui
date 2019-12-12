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
package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.ModeEnum;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.gui.GuiToken;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.parser.slot.SlotToken;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.server.ServerType;
import com.clubobsidian.dynamicgui.util.ChatColor;
import com.clubobsidian.dynamicgui.util.FunctionUtil;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;

public class GuiManager {

	private static GuiManager instance;
	
	private Map<String, Gui> guis;
	private Map<UUID, Gui> playerGuis;
	private Map<String, Gui> cachedGuis;
	private Map<String, List<MacroToken>> globalMacros;
	private Map<String, List<MacroToken>> cachedGlobalMacros;
	private Map<String, Long> guiTimestamps;
	private Map<String, Long> globalMacrosTimestamps;
	private boolean macrosModified;
	private GuiManager()
	{
		this.guis = new HashMap<>();
		this.playerGuis = new HashMap<>();
		this.cachedGuis = new HashMap<>();
		this.globalMacros = new LinkedHashMap<>();
		this.cachedGlobalMacros = new HashMap<>();
		this.guiTimestamps = new HashMap<>();
		this.globalMacrosTimestamps = new HashMap<>();
		this.macrosModified = false;
	}
	
	public static GuiManager get()
	{
		if(instance == null)
		{
			instance = new GuiManager();
			instance.loadGlobalMacros();
			instance.loadGuis();
		}
		return instance;
	}
	
	public boolean hasGuiName(String name)
	{
		return this.guis.containsKey(name);
	}
	
	public Gui getGuiByName(String name)
	{
		Gui gui = this.guis.get(name);
		if(gui != null)
		{
			return gui.clone();
		}
		
		return null;
	}

	public void reloadGuis()
	{
		DynamicGui.get().getLogger().info("Force reloading guis!");
		DynamicGui.get().getPlugin().unloadCommands();
		this.cachedGuis = this.guis;
		this.guis = new HashMap<>();
		this.cachedGlobalMacros = this.globalMacros;
		this.globalMacros = new HashMap<>();
		this.loadGlobalMacros();
		this.loadGuis();
	}
	
	public List<Gui> getGuis()
	{
		return new ArrayList<>(this.guis.values());
	}
	
	public Map<UUID, Gui> getPlayerGuis()
	{
		return this.playerGuis;
	}
	
	public boolean hasGuiCurrently(PlayerWrapper<?> playerWrapper)
	{
		return this.playerGuis.get(playerWrapper.getUniqueId()) != null;
	}
	
	public void cleanupGui(PlayerWrapper<?> playerWrapper)
	{
		this.playerGuis.remove(playerWrapper.getUniqueId());
	}

	public Gui getCurrentGui(PlayerWrapper<?> playerWrapper)
	{
		return this.playerGuis.get(playerWrapper.getUniqueId());
	}
	
	public boolean openGui(Object player, String guiName)
	{
		return this.openGui(EntityManager.get().createPlayerWrapper(player), guiName);
	}
	
	public boolean openGui(Object player, Gui gui)
	{
		return this.openGui(EntityManager.get().createPlayerWrapper(player), gui);
	}
	
	public boolean openGui(PlayerWrapper<?> playerWrapper, String guiName)
	{
		return this.openGui(playerWrapper, this.getGuiByName(guiName));
	}
	
	public boolean openGui(PlayerWrapper<?> playerWrapper, Gui gui)
	{
		if(gui == null)
		{
			playerWrapper.sendMessage(DynamicGui.get().getNoGui());
			return false;
		}
		
		Gui clonedGui = gui.clone();
		
		//Run gui load functions
		boolean ran = FunctionUtil.tryFunctions(clonedGui, FunctionType.LOAD, playerWrapper);
		
		if(ran)
		{
			InventoryWrapper<?> inventoryWrapper = clonedGui.buildInventory(playerWrapper);
			
			//Run slot load functions
			for(Slot slot : clonedGui.getSlots())
			{
				FunctionUtil.tryFunctions(slot, FunctionType.LOAD, playerWrapper);
			}
			
			if(inventoryWrapper == null)
				return false;
			
			if(DynamicGui.get().getServer().getType() == ServerType.SPONGE)
			{
				DynamicGui.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGui.get().getPlugin(), () -> 
				{
					playerWrapper.openInventory(inventoryWrapper);
				}, 1L);
			}
			else
			{
				playerWrapper.openInventory(inventoryWrapper);
			}
			this.playerGuis.put(playerWrapper.getUniqueId(), clonedGui);
			DynamicGui.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGui.get().getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					playerWrapper.updateInventory();
				}
			},2L);
		}
		return ran;
	}
	
	private void loadGlobalMacros()
	{
		File macroFolder = DynamicGui.get().getPlugin().getMacroFolder();
		
		Collection<File> macroFiles = FileUtils.listFiles(macroFolder, new String[]{"yml", "json", "conf", "xml"}, true);
		
		for(File file : macroFiles)
		{
			String macroName = file.getName().substring(0, file.getName().lastIndexOf("."));
			Long fileModified = file.lastModified();
			Long cacheModified = this.globalMacrosTimestamps.get(macroName);
			if(cacheModified == null || !fileModified.equals(cacheModified))
			{
				List<MacroToken> tokens = new ArrayList<>();
				Configuration config = Configuration.load(file);
				for(String key : config.getKeys())
				{
					ConfigurationSection section = config.getConfigurationSection(key);
					MacroToken token = new MacroToken(section);
					tokens.add(token);
				}
				
				this.macrosModified = true;
				this.globalMacrosTimestamps.put(macroName, fileModified);
				this.globalMacros.put(macroName, tokens);
			}
			else
			{
				List<MacroToken> cachedTokens = this.cachedGlobalMacros.get(macroName);
				this.globalMacros.put(macroName, cachedTokens);
			}
		}
		
		Iterator<Entry<String, List<MacroToken>>> it = this.cachedGlobalMacros.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String, List<MacroToken>> next = it.next();
			String macroName = next.getKey();
			if(!this.globalMacros.containsKey(macroName))
			{
				it.remove();
				this.globalMacrosTimestamps.remove(macroName);
			}
		}
	}
	
	private void loadGuis()
	{
		this.loadFileGuis();
		this.loadRemoteGuis();
		this.cleanupGuis();
	}
	
	private void loadFileGuis()
	{
		File guiFolder = DynamicGui.get().getPlugin().getGuiFolder();
		
		Collection<File> ar = FileUtils.listFiles(guiFolder, new String[]{"yml", "json", "conf", "xml"}, true);
		
		if(ar.size() != 0)
		{
			for(File file : ar)
			{
				try
				{
					
					String guiName = file.getName().substring(0, file.getName().lastIndexOf("."));
					Long modifiedTime = file.lastModified();
					Long cacheModifiedTime = this.guiTimestamps.get(guiName);
					if(!this.macrosModified && cacheModifiedTime != null && cacheModifiedTime.equals(modifiedTime))
					{
						Gui cachedGui = this.cachedGuis.get(guiName);
						this.guis.put(guiName, cachedGui);
					}
					else
					{
						Configuration yaml = Configuration.load(file);
						this.guiTimestamps.put(guiName, modifiedTime);
						this.loadGuiFromConfiguration(guiName, yaml);
					}
				}	
				catch(NullPointerException ex)
				{
					DynamicGui.get().getLogger().info("Error loading in file: " + file.getName());
					ex.printStackTrace();
				}	
			}
		} 
		else 
		{
			DynamicGui.get().getLogger().error("No guis found, please add guis or issues may occur!");
		}
		
		this.macrosModified = false;
	}
	
	private void loadRemoteGuis()
	{
		File configFile = new File(DynamicGui.get().getPlugin().getDataFolder(), "config.yml");
		File tempDirectory = new File(DynamicGui.get().getPlugin().getDataFolder(), "temp");
		if(tempDirectory.exists())
		{
			try 
			{
				FileUtils.deleteDirectory(tempDirectory);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		tempDirectory.mkdir();
		
		Configuration config = Configuration.load(configFile);
		if(config.get("remote-guis") != null)
		{
			ConfigurationSection remote = config.getConfigurationSection("remote-guis");
			for(String key :  remote.getKeys())
			{
				ConfigurationSection guiSection = remote.getConfigurationSection(key);
				String strUrl = guiSection.getString("url");
				try 
				{
					URL url = new URL(strUrl);
					String guiName = guiSection.getString("file-name");
					File backupFile = new File(DynamicGui.get().getPlugin().getGuiFolder(), guiName);
					File tempFile = new File(tempDirectory, guiName);
					Configuration guiConfiguration = Configuration.load(url, tempFile, backupFile);
					this.loadGuiFromConfiguration(guiName, guiConfiguration);
				} 
				catch (MalformedURLException e) 
				{
					e.printStackTrace();
					DynamicGui.get().getLogger().error("An error occured when loading from the url " + strUrl + " please ensure you have the correct url.");
				}
			}
		}
	}
	
	private void cleanupGuis()
	{
		Iterator<Entry<String, Gui>> it = this.cachedGuis.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String, Gui> next = it.next();
			String guiName = next.getKey();
			if(!this.guis.containsKey(guiName))
			{
				it.remove();
				this.guiTimestamps.remove(guiName);
			}
		}
	}
	
	private void loadGuiFromConfiguration(String guiName, Configuration config)
	{
		LoggerWrapper<?> logger = DynamicGui.get().getLogger();
		
		GuiToken guiToken = new GuiToken(config);
		List<MacroToken> guiTokens = new ArrayList<>();
		List<String> loadMacros = guiToken.getLoadMacros();
		
		if(loadMacros.size() > 0)
		{
			for(String macro : loadMacros)
			{
				List<MacroToken> macroTokens = this.globalMacros.get(macro);
				if(macroTokens != null)
				{
					for(MacroToken t : macroTokens)
					{
						guiTokens.add(t);
					}
				}
				else
				{
					logger.error("Invalid global macro specified " + macro + " in gui \"" + guiName + "\"");
				}
			}
			
			guiToken = new GuiToken(config, guiTokens);
		}
		
		List<Slot> slots = this.createSlots(guiToken);
		final Gui gui = this.createGui(guiToken, guiName, slots, DynamicGui.get().getPlugin());

		this.guis.put(guiName, gui);
		logger.info("gui \"" + gui.getName() + "\" has been loaded!");
	}

	private List<Slot> createSlots(GuiToken guiToken)
	{
		List<Slot> slots = new ArrayList<>();
		Iterator<Entry<Integer, SlotToken>> it = guiToken.getSlots().entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Integer, SlotToken> next = it.next();
			int index = next.getKey();
			SlotToken slotToken = next.getValue();
			
			String icon = MaterialManager.get().normalizeMaterial(slotToken.getIcon());
			String name = slotToken.getName();

			if(name != null)
			{
				name = ChatColor.translateAlternateColorCodes('&', name);
			}

			String nbt = slotToken.getNbt();

			List<String> lore = new ArrayList<>();
			for(String ls : slotToken.getLore())
			{
				lore.add(ChatColor.translateAlternateColorCodes('&', ls));
			}

			List<EnchantmentWrapper> enchants = new ArrayList<EnchantmentWrapper>();
				
			for(String ench : slotToken.getEnchants())
			{
					String[] args = ench.split(",");
					enchants.add(new EnchantmentWrapper(args[0], Integer.parseInt(args[1])));
			}
			
			int amount = slotToken.getAmount();
			
			boolean close = slotToken.isClosed();
			
			short data = slotToken.getData();
			
			boolean glow = slotToken.getGlow();
			
			int updateInterval = slotToken.getUpdateInterval();

			Map<String, String> metadata = slotToken.getMetadata();
			
			slots.add(new Slot(index, amount, icon, name, nbt, data, glow, close, lore, enchants, slotToken.getFunctionTree(), updateInterval, metadata));
		}

		
		return slots;
	}

	private Gui createGui(final GuiToken guiToken, final String guiName, final List<Slot> slots, final DynamicGuiPlugin plugin)
	{
		String type = guiToken.getType();
		String title = guiToken.getTitle();
		int rows = guiToken.getRows();
		
		for(String alias : guiToken.getAlias())
		{
			plugin.createCommand(guiName, alias);
		}

		boolean close = guiToken.isClosed();

		List<LocationWrapper<?>> locations = new ArrayList<>(); 
		for(String location : guiToken.getLocations())
		{
			locations.add(LocationManager.get().toLocationWrapper(location));
		}

		ModeEnum modeEnum = ModeEnum.valueOf(guiToken.getMode().toString());
		
		Map<String, List<Integer>> npcIds = guiToken.getNpcs();
		
		return new Gui(guiName, type, title, rows, close, modeEnum, npcIds, slots, locations, guiToken.getFunctions());
	}
}