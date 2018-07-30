package me.virustotal.dynamicgui.effect;

import java.io.Serializable;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public class ParticleWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5003322741003989392L;
	private Effect effect;
	private int data;
	
	public ParticleWrapper(String str)
	{
		String[] args = str.split(",");
		this.effect = Effect.valueOf(args[0]);
		this.data = Integer.parseInt(args[1]);
	}
	
	@SuppressWarnings("deprecation")
	public void spawnParticles(PlayerWrapper<T> player)
	{
		player.playEffect(player.getLocation(), this.effect, this.data);
	}	
}