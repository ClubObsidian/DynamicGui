package me.virustotal.dynamicgui.test;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestReflection {

	public static void test()
	{
		Bukkit.broadcastMessage("Pure is a noob!");
	}
	
	public static boolean testBoolean()
	{
		return true;
	}
	
	public static void saySomethingPlayer(Player player, String toSay)
	{
		Bukkit.broadcastMessage(toSay.replace("%name%", player.getName()));
	}
	
	public static void saySomethingName(String player, String toSay)
	{
		Bukkit.broadcastMessage(toSay.replace("%name%", player));
	}
	
	public static void saySomethingBoolean(Boolean data)
	{
		Bukkit.broadcastMessage("It is " + data + " that we should be negative!");
	}
	
	public static void saySomethingInteger(Integer data)
	{
		Bukkit.broadcastMessage("My favorite whole number is " + data);
	}
	
	public static void saySomethingDouble(Double data)
	{
		Bukkit.broadcastMessage("My favorite decimal number is " + data);
	}
}
