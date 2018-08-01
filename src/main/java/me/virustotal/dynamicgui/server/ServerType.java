package me.virustotal.dynamicgui.server;

public enum ServerType {

	SPONGE,
	SPIGOT;
	
	private static ServerType serverType;
	
	public static boolean setServerType(ServerType serverType)
	{
		if(serverType == null)
		{
			ServerType.serverType = serverType;
			return true;
		}
		return false;
	}
	
	public static ServerType get()
	{
		return ServerType.serverType;
	}
}