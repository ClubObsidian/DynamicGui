/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.sound;

import java.io.Serializable;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

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
	
	public SoundWrapper(String sound, Float volume, Float pitch)
	{
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public void playSoundToPlayer(PlayerWrapper<?> player)
	{
		player.playSound(this.sound, this.volume, this.pitch);
	}
}