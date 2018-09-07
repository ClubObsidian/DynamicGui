/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.function;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

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
