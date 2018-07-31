package me.virustotal.dynamicgui.objects.function;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
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
	public boolean function(PlayerWrapper<?> player)
	{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(this.getData());
		player.sendPluginMessage(DynamicGUI.getInstance().getPlugin(), "BungeeCord", out.toByteArray());
		return true;
	}
}