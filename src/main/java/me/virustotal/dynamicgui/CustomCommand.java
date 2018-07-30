package me.virustotal.dynamicgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CustomCommand extends Command {
    
    private CommandExecutor executer = null;

    public CustomCommand(String name) 
    {
        super(name);
    }

    public boolean execute(CommandSender sender, String commandLabel,String[] args) 
    {
        if(this.executer != null)
        {
            this.executer.onCommand(sender, this, commandLabel,args);
        }
        return false;
    }
   
    public void setExecutor(CommandExecutor executer)
    {
        this.executer = executer;
    }
}