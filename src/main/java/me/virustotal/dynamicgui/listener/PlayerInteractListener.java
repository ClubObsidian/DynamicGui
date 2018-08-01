package me.virustotal.dynamicgui.listener;

import java.util.ArrayList;
import java.util.List;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.objects.CLocation;

public class PlayerInteractListener<T,U> implements Listener{

	private DynamicGUI<T,U> plugin;
	public PlayerInteractListener(DynamicGUI<T,U> plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void interact(final PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			for(GUI gui : GuiApi.getGuis())
			{
				if(gui.getLocs() != null)
				{
					Block block = e.getClickedBlock();
					int x = block.getX();
					int y = block.getY();
					int z = block.getZ();
					World world = block.getWorld();

					List<Location> locs = new ArrayList<Location>();
					for(CLocation cLocation : gui.getLocs())
					{
						locs.add(cLocation.toLocation());
					}
					
					for(Location myLoc : locs)
					{
						int myX = myLoc.getBlockX();
						int myY = myLoc.getBlockY();
						int myZ = myLoc.getBlockZ();
						World myWorld = myLoc.getWorld();
						if(myX == x && myY == y && myZ == z && myWorld == world)
						{
							e.getPlayer().chat("/gui " + gui.getName());
							e.setCancelled(true);
							break;
						}
					}
				}
			}
		}
	}
}