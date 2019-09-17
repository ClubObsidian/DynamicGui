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
package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.fuzzutil.StringFuzz;

public class FunctionManager {
	
	private static FunctionManager instance;
	
	private Map<String, Function> functions;
	private FunctionManager()
	{
		this.functions = new HashMap<>();
	}
	
	public static FunctionManager get()
	{
		if(instance == null)
		{
			instance = new FunctionManager();
		}
		return instance;
	}
	
	public Function getFunctionByName(String functionName)
	{
		String normalized = StringFuzz.normalize(functionName);
		Function function = this.functions.get(normalized);
		if(function == null)
		{
			return null;
		}
		
		return function.clone();
	}
	
	public List<Function> getFunctions()
	{
		List<Function> funcs = new ArrayList<>();
		funcs.addAll(this.functions.values());
		return funcs;
	}
	
	public boolean addFunction(Function function)
	{
		return this.functions.put(function.getName(), function) != null;
	}

	public boolean removeFunctionByName(String functionName)
	{
		String normalized = StringFuzz.normalize(functionName);
		return this.functions.keySet().remove(normalized);
	}
}