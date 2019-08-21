/*
   Copyright 2019 Club Obsidian and contributors.

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

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;

public final class FunctionUtil {
	
	private FunctionUtil() {}
	
	public static boolean tryFunctions(FunctionOwner owner, FunctionType type, PlayerWrapper<?> playerWrapper)
	{
		if(owner instanceof Gui)
		{
			Gui gui = (Gui) owner;
			FunctionTree tree = gui.getToken().getFunctions();
			return recurFunctionNodes(tree.getRootNodes(), type, playerWrapper);
		}
		else if(owner instanceof Slot)
		{
			Slot slot = (Slot) owner;
			FunctionTree tree = slot.getToken().getFunctionTree();
			return recurFunctionNodes(tree.getRootNodes(), type, playerWrapper);
		}
		
		return false;
	}
	
	private static boolean recurFunctionNodes(List<FunctionNode> functionNodes, FunctionType type, PlayerWrapper<?> playerWrapper)
	{
		for(FunctionNode node : functionNodes)
		{
			FunctionToken functionToken = node.getToken();
			if(functionToken.getTypes().contains(type))
			{
				if(type != FunctionType.FAIL)
				{
					boolean ran = runFunctionData(functionToken.getFunctions(), playerWrapper);
					if(!ran)
					{
						runFunctionData(functionToken.getFailOnFunctions(), playerWrapper);
						return false;
					}
				}
			}
			return recurFunctionNodes(node.getChildren(), type, playerWrapper);
		}
		return true;
	}
	
	private static boolean runFunctionData(List<FunctionData> datas, PlayerWrapper<?> playerWrapper)
	{
		for(FunctionData data : datas)
		{
			Function function = FunctionManager.get().getFunctionByName(data.getName());
			if(function == null)
			{
				DynamicGui.get().getLogger().error("Invalid function " + data.getName());
				return false;
			}
			if(data.getData() != null)
			{
				function.setData(data.getData());
			}
			boolean ran = function.function(playerWrapper);
			if(!ran)
			{
				return false;
			}	
		}
		return true;
	}
}