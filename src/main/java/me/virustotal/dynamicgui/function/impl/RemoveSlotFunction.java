package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.manager.inventory.ItemStackManager;

public class RemoveSlotFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88925446185236878L;

	public RemoveSlotFunction(String name) 
	{
		super(name);
	}

	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		if(this.getData().equalsIgnoreCase("this"))
		{
			Slot slot = this.getOwner();
			if(slot != null)
			{
				if(playerWrapper.getOpenInventoryWrapper().getInventory() != null)
				{
					InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
					GUI gui = GuiApi.getCurrentGUI(playerWrapper);
					if(inv != null && gui != null)
					{
						for(Slot s : gui.getSlots())
						{
							ItemStackWrapper<?> item = s.getItemStack();
							if(item.getItemStack() != null)
							{
								try
								{
									if(this.getOwner().getIndex() == s.getIndex())
									{
										inv.setItem(this.getOwner().getIndex(), ItemStackManager.get().createItemStackWrapper("AIR", 1));
										break;
									}
								}
								catch(SecurityException | IllegalArgumentException ex)
								{
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		return true;
	}	
}
