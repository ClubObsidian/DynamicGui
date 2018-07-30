package me.virustotal.dynamicgui.economy;

import java.math.BigDecimal;

public interface Economy<P> {

	public boolean setup();
	public BigDecimal getBalance(P player);
	public boolean withdraw(P player, BigDecimal amt);
	public boolean deposit(P player, BigDecimal amt);

}
