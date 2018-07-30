package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Function;
import me.virustotal.dynamicgui.objects.ParticleWrapper;

public class ParticleFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719256169872302172L;

	public ParticleFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(Player player)
	{
		ParticleWrapper wrapper = new ParticleWrapper(this.getData());
		wrapper.spawnParticles(player);
		return true;
	}	
}