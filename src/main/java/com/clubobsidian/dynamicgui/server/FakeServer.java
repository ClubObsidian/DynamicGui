/*   Copyright 2018 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.server;

import java.util.Collection;
import java.util.UUID;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.scheduler.Scheduler;

public abstract class FakeServer {

	private Scheduler scheduler;
	public FakeServer(Scheduler scheduler)
	{
		this.scheduler = scheduler;
	}
	
	public Scheduler getScheduler()
	{
		return this.scheduler;
	}
	
	public abstract void broadcastMessage(String message);
	public abstract void dispatchServerCommand(String command);
	public abstract PlayerWrapper<?> getPlayer(UUID uuid);
	public abstract PlayerWrapper<?> getPlayer(String name);
	public abstract Collection<PlayerWrapper<?>> getOnlinePlayers();
	public abstract int getGlobalPlayerCount();
	public abstract ServerType getType();
	public abstract void registerOutgoingPluginChannel(DynamicGuiPlugin plugin, String channel);
	public abstract void registerIncomingPluginChannel(DynamicGuiPlugin plugin, String channel, MessagingRunnable runnable);
	
}
