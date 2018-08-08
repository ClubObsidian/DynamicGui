package me.virustotal.dynamicgui.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLoggerWrapper<T extends Logger> extends LoggerWrapper<T> {

	public JavaLoggerWrapper(T logger) 
	{
		super(logger);
	}

	@Override
	public void info(String message) 
	{
		this.getLogger().log(Level.INFO, message);
	}

	@Override
	public void error(String message) 
	{
		this.getLogger().log(Level.SEVERE, message);
	}
}