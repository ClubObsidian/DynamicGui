package me.virustotal.dynamicgui.economy.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;

import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.plugin.impl.SpongePlugin;

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
				Sponge.getCauseStackManager().pushCauseFrame()
				.pushCause(this)
				.addContext(EventContextKeys.PLUGIN, Sponge.getPluginManager().getPlugin(SpongePlugin.PLUGIN_ID).get())
				.getCurrentCause()).getResult() == ResultType.SUCCESS;
	}

	@Override
	public boolean deposit(PlayerWrapper<?> playerWrapper, BigDecimal amt) {
		// TODO Auto-generated method stub
		return false;
	}
}
