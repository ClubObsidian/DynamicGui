package me.virustotal.dynamicgui.economy;

import java.math.BigDecimal;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public interface Economy {

	public boolean setup();
	public BigDecimal getBalance(PlayerWrapper<?> playerWrapper);
	public boolean withdraw(PlayerWrapper<?> playerWrapper, BigDecimal amt);
	public boolean deposit(PlayerWrapper<?> playerWrapper, BigDecimal amt);

}
