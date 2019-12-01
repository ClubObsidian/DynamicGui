/*
   Copyright 2019 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.event.block;


import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.LocationEvent;
import com.clubobsidian.dynamicgui.event.player.Action;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.trident.Cancelable;

public class PlayerInteractEvent extends LocationEvent implements Cancelable {

	private Action action;
	private boolean canceled;
	public PlayerInteractEvent(PlayerWrapper<?> playerWrapper, LocationWrapper<?> locationWrapper, Action action) 
	{
		super(playerWrapper, locationWrapper);
		this.action = action;
		this.canceled = false;
	}
	
	public Action getAction()
	{
		return this.action;
	}

	@Override
	public boolean isCanceled() 
	{
		return this.canceled;
	}

	@Override
	public void setCanceled(boolean cancel) 
	{
		this.canceled = cancel;
	}
}