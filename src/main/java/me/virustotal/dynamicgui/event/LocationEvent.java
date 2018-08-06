package me.virustotal.dynamicgui.event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.world.LocationWrapper;

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