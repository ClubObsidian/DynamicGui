package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;

public class SetTypeFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6943230273788425141L;

	public SetTypeFunction(String name) 
	{
		super(name);
	}
	
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		Slot slot = this.getOwner();
		if(slot != null)
		{
			if(playerWrapper.getOpenInventoryWrapper() != null)
			{
				InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
				GUI gui = GuiApi.getCurrentGUI(playerWrapper);
				if(inv != null && gui != null)
				{
					for(Slot s : gui.getSlots())
					{
						ItemStackWrapper<?> item = inv.getItem(s.getIndex());
						if(item.getItemStack() != null)
						{
							if(this.getOwner().getIndex() == s.getIndex())
							{
								item.setType(this.getData());
								inv.setItem(this.getOwner().getIndex(), item);
								DynamicGUI.get().getLogger().info("From set type function, itemstack is: " + item.getItemStack());
								break;
							}
						}
					}
				}
			}
		}
		return true;
	}	
}