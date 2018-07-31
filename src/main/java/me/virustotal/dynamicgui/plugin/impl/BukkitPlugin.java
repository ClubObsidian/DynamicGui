package me.virustotal.dynamicgui.plugin.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import java.util.List;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.economy.impl.VaultEconomy;
import me.virustotal.dynamicgui.listener.bukkit.EntityClickListener;
import me.virustotal.dynamicgui.npc.NPC;
import me.virustotal.dynamicgui.npc.NPCRegistry;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;
import me.virustotal.dynamicgui.util.server.ServerType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BukkitPlugin<T extends org.bukkit.entity.Player, U extends org.bukkit.entity.Entity> extends JavaPlugin implements DynamicGUIPlugin<T,U>, PluginMessageListener {

	private Economy<T> economy;
	private List<NPCRegistry<U>> npcRegistries;
	
	@Override
	public void onEnable()
	{
		this.start();
	}
	
	@Override
	public void start() 
	{
		ServerType.setServerType(ServerType.SPIGOT);
		DynamicGUI.setInstance(new DynamicGUI<T,U>(this));
		this.economy = new VaultEconomy<T>();
		if(!this.economy.setup())
		{
			this.economy = null;
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(new EntityClickListener(), this);
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
	
	@Override
	public int getPlayerCount() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
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
	}

	@Override
	public Economy<T> getEconomy() 
	{
		return this.economy;
	}

	@Override
	public List<NPCRegistry<U>> getNPCRegistries() 
	{
		return this.npcRegistries;
	}

	@Override
	public boolean isNPC(U entity) 
	{
		for(NPCRegistry<U> registry : this.getNPCRegistries())
		{
			if(registry.isNPC(entity))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public NPC<U> getNPC(U entity) 
	{
		for(NPCRegistry<U> registry : this.getNPCRegistries())
		{
			NPC<U> npc = registry.getNPC(entity);
			if(npc != null)
			{
				return npc;
			}
		}
		return null;
	}
}
