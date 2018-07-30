package me.virustotal.dynamicgui.economy;

import java.math.BigDecimal;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public interface Economy<T> {

	public boolean setup();
	public BigDecimal getBalance(PlayerWrapper<T> t);
	public boolean withdraw(PlayerWrapper<T> player, BigDecimal amt);
	public boolean deposit(PlayerWrapper<T> player, BigDecimal amt);

}
