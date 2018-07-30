package me.virustotal.dynamicgui.objects.function;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.objects.Function;

public class SendFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2329250573729355253L;

	public SendFunction(String name, String data) 
	{
		super(name, data);
	}
	
	public SendFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(Player player)
	{
		if(DynamicGUI.getPlugin().getBungeeCord() || DynamicGUI.getPlugin().getRedisBungee())
		{
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(this.getData());
			player.sendPluginMessage(DynamicGUI.getPlugin(), "BungeeCord", out.toByteArray());
			return true;
		}
			DynamicGUI.getPlugin().getLogger().log(Level.INFO, "Cannot send users via BungeeCord, BungeeCord is disabled!");
			return false;
	}
}