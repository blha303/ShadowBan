package me.blha303.shadowban;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ShadowBan extends JavaPlugin implements Listener {

    List<String> playerlist = new ArrayList<String>();

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().addDefault("shadowbannedList", playerlist);
        getConfig().options().copyDefaults(true);
        saveConfig();
        playerlist = getConfig().getStringList("shadowbannedList");
    }
    
    public void onPlayerJoin(AsyncPlayerChatEvent event) {
        if (playerlist.contains(event.getPlayer().getName())) {
            event.getRecipients().clear();
            event.getRecipients().add(event.getPlayer());
            this.getLogger().info(event.getPlayer().getName() + " tried to say: " + event.getMessage());
        }
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                for (String arg : args) {
                    if (getServer().getPlayer(arg) != null) {
                        playerlist.add(getServer().getPlayer(arg).getName());
                    } else {
                        sender.sendMessage(String.format("[%s] Could not find player \"%s\"", getDescription().getName(), arg));
                        return true;
                    }
                    sender.sendMessage(String.format("[%s] %s added to shadowban list", getDescription().getName(), getServer().getPlayer(arg).getName()));
                    return true;
                }
            } else {
                return false;
            }
        } else {
            if (args.length >= 1) {
                for (String arg : args) {
                    if (getServer().getPlayer(arg) != null) {
                        playerlist.add(getServer().getPlayer(arg).getName());
                    } else {
                        sender.sendMessage(String.format("[%s] Could not find player \"%s\"", getDescription().getName(), arg));
                        return true;
                    }
                    sender.sendMessage(String.format("[%s] %s added to shadowban list", getDescription().getName(), getServer().getPlayer(arg).getName()));
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
