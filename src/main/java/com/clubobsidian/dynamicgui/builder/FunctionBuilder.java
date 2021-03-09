/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.builder;

import com.clubobsidian.dynamicgui.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.parser.function.FunctionModifier;

public class FunctionBuilder {
	
	private String name;
	private String data;
	private FunctionModifier modifier = FunctionModifier.NONE;
	
	public FunctionBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public FunctionBuilder setData(String data)
	{
		this.data = data;
		return this;
	}

	public FunctionBuilder setModifier(FunctionModifier modifier)
	{
		this.modifier = modifier;
		return this;
	}

	public FunctionData create(String name, String data)
	{
		return this.create(name, data, FunctionModifier.NONE);
	}


	public FunctionData create(String name, String data, FunctionModifier modifier)
	{
		this.setName(name);
		this.setData(data);
		this.setModifier(modifier);
		return this.build();
	}
	
	public FunctionData build()
	{
		return new FunctionData(this.name, this.data, this.modifier);
	}
}