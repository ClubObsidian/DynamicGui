package com.clubobsidian.dynamicgui.configuration;

import java.io.File;

public class UnknownFileTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5825723878989203063L;
	
	public UnknownFileTypeException(File file)
	{
		super("Unknown file type for configuration file " + file.getName());
	}
}