package com.clubobsidian.dynamicgui.event;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

public class LocationEvent extends PlayerEvent {

	private LocationWrapper<?> locationWrapper;
	public LocationEvent(PlayerWrapper<?> playerWrapper, LocationWrapper<?> locationWrapper) 
	{
		super(playerWrapper);
		this.locationWrapper = locationWrapper;
	}
	
	public LocationWrapper<?> getLocationWrapper()
	{
		return this.locationWrapper;
	}
}