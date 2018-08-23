package com.clubobsidian.dynamicgui.function;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;

/**
 * 
 * @author virustotal
 *
 * This function is empty on purpose. Meant as a data
 * holder for internal usage. By design this is how DynamicGUI
 * was written but Function is now an abstract class. This is
 * subject to change in the future.
 */
public class EmptyFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1802471782257186804L;

	public EmptyFunction(String name, String data) 
	{
		super(name, data);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		return false;
	}
}