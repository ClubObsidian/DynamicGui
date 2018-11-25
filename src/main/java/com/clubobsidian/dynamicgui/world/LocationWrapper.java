/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.world;

import java.io.Serializable;

public abstract class LocationWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3610165660936041660L;
	
	private int x;
	private int y;
	private int z;
	private String worldName;
	public LocationWrapper(int x, int y, int z, String worldName)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	public int getZ()
	{
		return this.z;
	}
	
	public String getWorldName()
	{
		return this.worldName;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(!(obj instanceof LocationWrapper))
			return false;
		
		LocationWrapper<?> other = (LocationWrapper<?>) obj;
		if(other.getX() != this.getX())
			return false;
		if(other.getY() != this.getY())
			return false;
		if(other.getZ() != this.getZ())
			return false;
		
		return other.getWorldName().equals(this.getWorldName());
	}
}
