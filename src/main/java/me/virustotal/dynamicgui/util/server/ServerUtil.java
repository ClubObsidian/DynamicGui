package me.virustotal.dynamicgui.util.server;

public final class ServerUtil {

	private ServerUtil() {}
	
	private static ServerType serverType = null;
	public static ServerType getServerType()
	{
		return serverType;
	}
}
