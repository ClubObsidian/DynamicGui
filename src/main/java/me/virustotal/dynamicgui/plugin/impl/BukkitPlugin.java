package me.virustotal.dynamicgui.plugin.impl;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.economy.impl.VaultEconomy;
import me.virustotal.dynamicgui.npc.NPC;
import me.virustotal.dynamicgui.npc.NPCRegistry;
import me.virustotal.dynamicgui.plugin.DynamicGUIPlugin;

public class BukkitPlugin<P extends org.bukkit.entity.Player, E extends org.bukkit.entity.Entity> extends JavaPlugin implements DynamicGUIPlugin<P,E>, PluginMessageListener {

	private Economy<P> economy;
	private List<NPCRegistry<E>> npcRegistries;
	
	@Override
	public void onEnable()
	{
		this.start();
	}
	
	@Override
	public void start() 
	{
		this.economy = new VaultEconomy<P>();
		if(!this.economy.setup())
		{
			this.economy = null;
		}
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
	public Economy<P> getEconomy() 
	{
		return this.economy;
	}

	@Override
	public List<NPCRegistry<E>> getNPCRegistries() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNPC(E entity) 
	{
		for(NPCRegistry<E> registry : this.getNPCRegistries())
		{
			if(registry.isNPC(entity))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public NPC<E> getNPC(E entity) 
	{
		for(NPCRegistry<E> registry : this.getNPCRegistries())
		{
			NPC<E> npc = registry.getNPC(entity);
			if(npc != null)
			{
				return npc;
			}
		}
		return null;
	}
}
