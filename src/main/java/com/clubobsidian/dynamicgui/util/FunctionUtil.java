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
			return recurFunctionNodes(null, owner, tree.getRootNodes(), type, playerWrapper);
		}
		else if(owner instanceof Slot)
		{
			Slot slot = (Slot) owner;
			FunctionTree tree = slot.getToken().getFunctionTree();
			return recurFunctionNodes(null, owner, tree.getRootNodes(), type, playerWrapper);
		}
		
		return false;
	}

	private static boolean recurFunctionNodes(FunctionResponse fail, FunctionOwner owner, List<FunctionNode> functionNodes, FunctionType type, PlayerWrapper<?> playerWrapper)
	{
		for(FunctionNode node : functionNodes)
		{
			FunctionToken functionToken = node.getToken();
			if(functionToken.getTypes().contains(type))
			{
				if(type != FunctionType.FAIL)
				{
					FunctionResponse response = runFunctionData(owner, functionToken.getFunctions(), playerWrapper);

					if(!response.result)
					{
						if(response.failedFunction == null)
						{
							return false;
						}
						
						recurFunctionNodes(response, owner, node.getChildren(), FunctionType.FAIL, playerWrapper);
						return false;
					}
				}
				else if(type == FunctionType.FAIL)
				{
					if(isFail(fail, functionToken))
					{
						FunctionResponse response = runFunctionData(owner, functionToken.getFunctions(), playerWrapper);
						if(!response.result)
						{
							recurFunctionNodes(response, owner, node.getChildren(), FunctionType.FAIL, playerWrapper);
							return false;
						}
					}
				}
			}

			return recurFunctionNodes(null, owner, node.getChildren(), type, playerWrapper);
		}
		return true;
	}

	private static FunctionResponse runFunctionData(FunctionOwner owner, List<FunctionData> datas, PlayerWrapper<?> playerWrapper)
	{
		for(FunctionData data : datas)
		{
			String functionName = data.getName();
			String functionData = data.getData();
			Function function = FunctionManager.get().getFunctionByName(functionName);
			if(function == null)
			{
				DynamicGui.get().getLogger().error("Invalid function " + data.getName());
				return new FunctionResponse(false);
			}
			if(data.getData() != null)
			{
				function.setOwner(owner);
				function.setData(functionData);
			}
			boolean ran = function.function(playerWrapper);
			if(!ran)
			{
				return new FunctionResponse(false, functionName, functionData);
			}	
		}
		return new FunctionResponse(true);
	}

	private static boolean isFail(FunctionResponse response, FunctionToken token)
	{
		for(FunctionData data : token.getFailOnFunctions())
		{
			if(data.getName().equals(response.failedFunction))
			{
				if(data.getData() == null)
				{
					return true;
				}
				else if(data.getData().equals(response.data))
				{
					return true;
				}
			}
		}
		
		return false;
	}

	private static class FunctionResponse 
	{	
		private boolean result;
		private String failedFunction;
		private String data;
		public FunctionResponse(boolean result)
		{
			this(result, null, null);
		}

		public FunctionResponse(boolean result, String failedFunction, String data)
		{
			this.result = result;
			this.failedFunction = failedFunction;
			this.data = data;
		}
	}
}