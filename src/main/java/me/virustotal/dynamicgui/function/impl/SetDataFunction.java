package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;

public class SetDataFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6943230273788425141L;

	public SetDataFunction(String name) 
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
								item.setDurability(Short.parseShort(this.getData()));
								inv.setItem(this.getOwner().getIndex(), item);
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
