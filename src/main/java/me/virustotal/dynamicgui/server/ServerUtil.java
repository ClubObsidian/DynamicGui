package me.virustotal.dynamicgui.server;

public final class ServerUtil {

	private ServerUtil() {}
	
	private static ServerType serverType = null;
	public static ServerType getServerType()
	{
		return serverType;
	}
}
