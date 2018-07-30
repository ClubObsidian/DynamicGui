package me.virustotal.dynamicgui.economy;

public interface Economy<P> {

	public void setup();
	public double getBalance(P player);
	public boolean withdraw(P player);
	public boolean deposit(P player);

}
