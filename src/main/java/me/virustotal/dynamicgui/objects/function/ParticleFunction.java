package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.effect.ParticleWrapper;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;

public class ParticleFunction<T> extends Function<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719256169872302172L;

	public ParticleFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<T> player)
	{
		ParticleWrapper wrapper = new ParticleWrapper(this.getData());
		wrapper.spawnParticles(player);
		return true;
	}	
}