package com.clubobsidian.dynamicgui.gui;

import java.util.List;

import com.clubobsidian.dynamicgui.function.Function;

public interface FunctionOwner {

	public List<Function> getFunctions();
	public List<Function> getFailFunctions(String key);

}