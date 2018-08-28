package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class SendFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2329250573729355253L;
	
	public SendFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(this.getData());
		playerWrapper.sendPluginMessage(DynamicGUI.get().getPlugin(), "BungeeCord", out.toByteArray());
		return true;
	}
}