/*   Copyright 2018 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.util;

import org.bukkit.Bukkit;

public class VersionUtil {

	public synchronized static String getVersion() 
	{
		String version = "";
		if(Bukkit.getServer() == null)
		{
			return null;
		}
		String name = Bukkit.getServer().getClass().getPackage().getName();
		version = name.substring(name.lastIndexOf('.') + 1);
		return version;
	}
}
