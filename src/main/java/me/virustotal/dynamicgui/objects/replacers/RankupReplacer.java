package me.virustotal.dynamicgui.objects.replacers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.fvza.rankup.util.Config;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.objects.Replacer;

public class RankupReplacer extends Replacer {

	public RankupReplacer(String toReplace) 
	{
		super(toReplace);
	}
	
	@Override
	public String replacement(String text, Player player)
	{
		String nextRank = Config.getRankToGroup(player);
		if(nextRank == null)
			return text.replace(this.getToReplace(), ChatColor.RED + "No Rank Found");
		Double rankPrice = Config.getGroupPrice(nextRank);
		Double currentBal = DynamicGUI.getInstance().getEconomy().getBalance(player.getName());
		return text.replace(this.getToReplace(), ChatColor.translateAlternateColorCodes('&', RankupReplacer.format(currentBal, rankPrice)));
	}
	
	private static String format(double value, double totalprice) 
	{
		int percentage = (int)Math.rint(value * 100.0D / totalprice) / 10;
		switch(percentage) {
		case 0:
			return "&8&l|&7&l||||||||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 1:
			return "&8&l|&a&l|&7&l|||||||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 2:
			return "&8&l|&a&l||&7&l||||||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 3:
			return "&8&l|&a&l|||&7&l|||||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 4:
			return "&8&l|&a&l||||&7&l||||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 5:
			return "&8&l|&a&l|||||&7&l|||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 6:
			return "&8&l|&a&l||||||&7&l||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 7:
			return "&8&l|&a&l|||||||&7&l|||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 8:
			return "&8&l|&a&l||||||||&7&l||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 9:
			return "&8&l|&a&l|||||||||&7&l|&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		case 10:
		default:
			return "&8&l|&a||||||||||&8&l| &a" + (int)Math.rint(value * 100.0D / totalprice) + "%";
		}
	}

}
