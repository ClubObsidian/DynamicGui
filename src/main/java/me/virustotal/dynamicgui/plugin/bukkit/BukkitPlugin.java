package me.virustotal.dynamicgui.plugin.bukkit;

import java.io.File;
import java.util.logging.Logger;
import java.util.List;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.command.bukkit.BukkitGUICommand;
import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.economy.bukkit.VaultEconomy;
import me.virustotal.dynamicgui.entity.EntityWrapper;
import me.virustotal.dynamicgui.listener.bukkit.EntityClickListener;
import me.virustotal.dynamicgui.listener.bukkit.InventoryClickListener;
import me.virustotal.dynamicgui.listener.bukkit.InventoryCloseListener;
import me.virustotal.dynamicgui.listener.bukkit.InventoryOpenListener;
import me.virustotal.dynamicgui.listener.bukkit.PlayerInteractListener;
import me.virustotal.dynamicgui.logger.JavaLoggerWrapper;
import me.virustotal.dynamicgui.npc.NPC;
import me.virustotal.dynamicgui.npc.NPCRegistry;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.server.bukkit.FakeBukkitServer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin<T extends Player, U extends Entity> extends JavaPlugin implements DynamicGUIPlugin<T,U> {

	private File configFile;
	private File guiFolder;
	private Economy economy;
	private List<NPCRegistry<U>> npcRegistries;
	
	@Override
	public void onEnable()
	{
		this.start();
	}
	
	@Override
	public void start() 
	{
		this.configFile = new File(this.getDataFolder(), "config.yml");
		this.guiFolder = new File(this.getDataFolder(), "guis");

		DynamicGUI.createInstance(this, new FakeBukkitServer(), new JavaLoggerWrapper<Logger>(this.getLogger()));
		this.economy = new VaultEconomy();
		if(!this.economy.setup())
		{
			this.economy = null;
		}
		
		this.getCommand("gui").setExecutor(new BukkitGUICommand());
		Bukkit.getServer().getPluginManager().registerEvents(new EntityClickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryOpenListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
	}

	@Override
	public void onDisable()
	{
		this.stop();
	}
	
	@Override
	public void stop() 
	{
		
		
	}

	//TODO
	/*@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) 
	{
		//System.out.println("received: " + channel);
		if (channel.equals("BungeeCord") || channel.equals("RedisBungee")) 
		{
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
			String packet = in.readUTF();
			if(packet != null)
			{
				if(packet.equals("PlayerCount"))
				{
					//System.out.println("player count");
					String server = in.readUTF();
					//System.out.println("server: " + server);
					if(this.serverPlayerCount.containsKey(server))
					{
						int playerCount = in.readInt();
						//System.out.println("count: " + playerCount);
						this.serverPlayerCount.put(server, playerCount);
					}
				}
			}
		}
	}*/

	@Override
	public Economy getEconomy() 
	{
		return this.economy;
	}

	@Override
	public List<NPCRegistry<U>> getNPCRegistries() 
	{
		return this.npcRegistries;
	}

	@Override
	public boolean isNPC(EntityWrapper<?> entity) 
	{
		for(NPCRegistry<?> registry : this.getNPCRegistries())
		{
			if(registry.isNPC(entity))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public NPC<U> getNPC(EntityWrapper<?> entityWrapper) 
	{
		for(NPCRegistry<U> registry : this.getNPCRegistries())
		{
			NPC<U> npc = registry.getNPC(entityWrapper);
			if(npc != null)
			{
				return npc;
			}
		}
		return null;
	}

	@Override
	public File getConfigFile() 
	{
		return this.configFile;
	}
	
	@Override
	public File getGuiFolder() 
	{
		return this.guiFolder;
	}

	@Override
	public void createCommand(String guiName, String alias) 
	{
		// TODO Auto-generated method stub
	}
}
