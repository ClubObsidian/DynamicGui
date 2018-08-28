package com.clubobsidian.dynamicgui.economy;

import java.math.BigDecimal;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public interface Economy {

	public boolean setup();
	public BigDecimal getBalance(PlayerWrapper<?> playerWrapper);
	public boolean withdraw(PlayerWrapper<?> playerWrapper, BigDecimal amt);
	public boolean deposit(PlayerWrapper<?> playerWrapper, BigDecimal amt);

}
