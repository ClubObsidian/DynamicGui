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
package com.clubobsidian.dynamicgui.economy.sponge;

import java.math.BigDecimal;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;

import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.plugin.sponge.SpongePlugin;

public class SpongeEconomy implements Economy {

	private EconomyService economy;
	
	@Override
	public boolean setup() 
	{
		Optional<EconomyService> economyService = Sponge.getServiceManager().provide(EconomyService.class);
		if(!economyService.isPresent())
		{
			return false;
		}
		return (this.economy = economyService.get()) != null;
	}
	
	@Override
	public BigDecimal getBalance(PlayerWrapper<?> playerWrapper) 
	{
		if(playerWrapper.getPlayer() == null)
			return new BigDecimal(-1);
		
		Player player = (Player) playerWrapper.getPlayer();
		Optional<UniqueAccount> account = this.economy.getOrCreateAccount(player.getUniqueId());
		if(!account.isPresent())
		{
			return new BigDecimal(-1);
		}
		return account.get().getBalance(this.economy.getDefaultCurrency());
	}

	@Override
	public boolean withdraw(PlayerWrapper<?> playerWrapper, BigDecimal amt) 
	{
		Optional<UniqueAccount> account = this.economy.getOrCreateAccount(playerWrapper.getUniqueId());
		if(!account.isPresent())
		{
			return false;
		}
		return account.get().withdraw(this.economy.getDefaultCurrency(), amt,
		this.createCurrencyCause()).getResult() == ResultType.SUCCESS;
	}

	@Override
	public boolean deposit(PlayerWrapper<?> playerWrapper, BigDecimal amt) 
	{
		Optional<UniqueAccount> account = this.economy.getOrCreateAccount(playerWrapper.getUniqueId());
		if(!account.isPresent())
		{
			return false;
		}
		return account.get().deposit(this.economy.getDefaultCurrency(), amt, 
		this.createCurrencyCause()).getResult() == ResultType.SUCCESS;
	}
	
	private Cause createCurrencyCause()
	{
		return Sponge.getCauseStackManager().pushCauseFrame()
		.pushCause(this)
		.addContext(EventContextKeys.PLUGIN, Sponge.getPluginManager().getPlugin(SpongePlugin.PLUGIN_ID).get())
		.getCurrentCause();
	}
}