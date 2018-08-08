package me.virustotal.dynamicgui.logger;

public abstract class LoggerWrapper<T> {

	private T logger;
	public LoggerWrapper(T logger) 
	{
		this.logger = logger;
	}
	
	public T getLogger()
	{
		return this.logger;
	}

	public abstract void info(String message);
	public abstract void error(String message);
}
