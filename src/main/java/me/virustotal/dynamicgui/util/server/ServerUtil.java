package me.virustotal.dynamicgui.util.server;

public final class ServerUtil {

	private ServerUtil() {}
	
	private static ServerType serverType;
	public static ServerType getServerType()
	{
		if(serverType == null)
		{
			try 
			{
				Class.forName("org.bukkit.Server");
				serverType = ServerType.SPIGOT;
			} 
			catch (ClassNotFoundException e) {}
			
			//Have this second in case of plugin compatibility mods like Pore
			try
			{
				Class.forName("org.spongepowered.api.Server");
				serverType = ServerType.SPONGE;
			}
			catch(ClassNotFoundException e) {}
			
		}
		return serverType;
	}
}
