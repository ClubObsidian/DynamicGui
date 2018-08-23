package com.clubobsidian.dynamicgui.logger;

import org.slf4j.Logger;

public class Sl4jLoggerWrapper<T extends Logger> extends LoggerWrapper<T> {

	public Sl4jLoggerWrapper(T logger) 
	{
		super(logger);
	}

	@Override
	public void info(String message) 
	{
		this.getLogger().info(message);
	}

	@Override
	public void error(String message) 
	{
		this.getLogger().info(message);
	}
}