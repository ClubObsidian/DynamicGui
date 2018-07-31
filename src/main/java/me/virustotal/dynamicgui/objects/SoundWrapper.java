package me.virustotal.dynamicgui.objects;

import java.io.Serializable;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public class SoundWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8096584636206059158L;
	private String sound;
	private Float volume;
	private Float pitch;
	
	public SoundWrapper(String str)
	{
		String[] args = str.split(",");
		this.sound = args[0];
		this.volume = Float.parseFloat(args[1]);
		this.pitch = Float.parseFloat(args[2]);
	}
	
	public SoundWrapper(Sound sound, Float volume, Float pitch)
	{
		this.sound = sound.toString();
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public void playSoundToPlayer(PlayerWrapper<?> player)
	{
		player.playSound(this.sound, this.volume, this.pitch);
	}
}