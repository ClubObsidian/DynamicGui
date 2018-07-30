package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.objects.SoundWrapper;

public class SoundFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8363807525418126179L;

	public SoundFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper player)
	{
		SoundWrapper wrapper = new SoundWrapper(this.getData());
		wrapper.playSoundToPlayer(player);
		return true;
	}
}