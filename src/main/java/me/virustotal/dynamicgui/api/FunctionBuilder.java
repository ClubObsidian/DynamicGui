package me.virustotal.dynamicgui.api;

import me.virustotal.dynamicgui.function.EmptyFunction;
import me.virustotal.dynamicgui.function.Function;

public class FunctionBuilder {
	
	private String name;
	private String data;
	
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
	
	public Function build()
	{
		return new EmptyFunction(this.name, this.data);
	}
}