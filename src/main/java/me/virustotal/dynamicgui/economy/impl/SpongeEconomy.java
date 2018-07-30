package me.virustotal.dynamicgui.economy.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.plugin.impl.SpongePlugin;

public class SpongeEconomy<P extends org.spongepowered.api.entity.living.player.Player> implements Economy<P> {

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
	public BigDecimal getBalance(P player) 
	{
		Optional<UniqueAccount> account = this.economy.getOrCreateAccount(player.getUniqueId());
		if(!account.isPresent())
		{
			return new BigDecimal(-1);
		}
		return account.get().getBalance(this.economy.getDefaultCurrency());
	}

	@Override
	public boolean withdraw(P player, BigDecimal amt) 
	{
		Optional<UniqueAccount> account = this.economy.getOrCreateAccount(player.getUniqueId());
		if(!account.isPresent())
		{
			return false;
		}
		return account.get().withdraw(this.economy.getDefaultCurrency(), amt,
				Sponge.getCauseStackManager().pushCauseFrame()
				.pushCause(this)
				.addContext(EventContextKeys.PLUGIN, Sponge.getPluginManager().getPlugin(SpongePlugin.PLUGIN_ID).get())
				.getCurrentCause()).getResult() == ResultType.SUCCESS;
	}

	@Override
	public boolean deposit(P player, BigDecimal amt) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}