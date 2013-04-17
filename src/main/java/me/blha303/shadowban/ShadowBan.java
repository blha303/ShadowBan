package me.blha303.shadowban;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Joiner;

public class ShadowBan extends JavaPlugin implements Listener {

    List<String> playerlist = new ArrayList<String>();
    boolean debug;

    public void onEnable() {
        saveDefaultConfig();
        debug = getConfig().getBoolean("debug");
        getServer().getPluginManager().registerEvents(this, this);
        debug("Events registered");
        playerlist = getConfig().getStringList("shadowbannedList");
    }

    public void onDisable() {
        saveConfig();
    }

    public void debug(String msg) {
        if (debug)
            this.getLogger().info("DEBUG: " + msg);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(AsyncPlayerChatEvent event) {
        debug("Entered PlayerChatEvent");
        if (playerlist.contains(event.getPlayer().getName())) {
            debug("Playerlist contains this name!");
            event.getRecipients().clear();
            debug("List cleared");
            event.getRecipients().add(event.getPlayer());
            debug("Recipient added");
            this.getLogger().info(event.getPlayer().getName() + " tried to say: " + event.getMessage());
            debug("Message sent");
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + getDescription().getName() + ChatColor.GRAY + "] ";
        if (command.getLabel().equalsIgnoreCase("shadowban")) {
            if (sender instanceof Player) {
                debug("Sender is player");
                if (args.length >= 1) {
                    if (sender.hasPermission("shadowban.use")) {
                        debug("One or more args");
                        for (String arg : args) {
                            debug("Entered for loop");
                            if (getServer().getPlayer(arg) != null) {
                                debug("Adding arg to playerlist");
                                playerlist.add(getServer().getPlayer(arg).getName());
                                debug("arg added");
                            } else {
                                debug("couldn't find player from arg");
                                sender.sendMessage(prefix + ChatColor.WHITE + "Could not find player \"" + ChatColor.RED + arg + ChatColor.WHITE + "\"");
                                return true;
                            }
                            getConfig().set("shadowbannedList", playerlist);
                            saveConfig();
                            sender.sendMessage(prefix + ChatColor.GREEN + getServer().getPlayer(arg) + " " + ChatColor.WHITE + "added to shadowban list");
                            return true;
                        }
                    } else {
                        sender.sendMessage(prefix + "You can't use this command.");
                        return true;
                    }
                } else {
                    debug("not enough args");
                    return false;
                }
            } else {
                if (args.length >= 1) {
                    for (String arg : args) {
                        if (getServer().getPlayer(arg) != null) {
                            playerlist.add(getServer().getPlayer(arg).getName());
                        } else {
                            sender.sendMessage(prefix + ChatColor.WHITE + "Could not find player \"" + ChatColor.RED + arg + ChatColor.WHITE + "\"");
                            return true;
                        }
                        getConfig().set("shadowbannedList", playerlist);
                        saveConfig();
                        sender.sendMessage(prefix + ChatColor.GREEN + getServer().getPlayer(arg) + " " + ChatColor.WHITE + "added to shadowban list");
                        return true;
                    }
                } else {
                    return false;
                }
            }
        } else if (command.getLabel().equalsIgnoreCase("shadowbanoffline")) {
            if (sender instanceof Player) {
                debug("Sender is player");
                if (args.length >= 1) {
                    if (sender.hasPermission("shadowban.offline")) {
                        debug("One or more args");
                        for (String arg : args) {
                            debug("Entered for loop");
                            debug("Adding arg to playerlist");
                            playerlist.add(arg);
                            debug("arg added");
                            getConfig().set("shadowbannedList", playerlist);
                            saveConfig();
                            sender.sendMessage(prefix + ChatColor.GREEN + arg + " " + ChatColor.WHITE + "added to shadowban list");
                            return true;
                        }
                    } else {
                        sender.sendMessage(prefix + "You can't use this command.");
                        return true;
                    }
                } else {
                    debug("not enough args");
                    return false;
                }
            } else {
                if (args.length >= 1) {
                    for (String arg : args) {
                        playerlist.add(arg);
                        getConfig().set("shadowbannedList", playerlist);
                        saveConfig();
                        sender.sendMessage(prefix + ChatColor.GREEN + arg + " " + ChatColor.WHITE + "added to shadowban list");
                        return true;
                    }
                } else {
                    return false;
                }
            }
        } else if (command.getLabel().equalsIgnoreCase("shadowunban")) {
            if (sender instanceof Player) {
                debug("Sender is player");
                if (args.length >= 1) {
                    if (sender.hasPermission("shadowban.unban")) {
                        debug("One or more args");
                        for (String arg : args) {
                            debug("Entered for loop");
                            debug("Removing arg from playerlist");
                            playerlist.remove(arg);
                            debug("arg removed");
                            getConfig().set("shadowbannedList", playerlist);
                            saveConfig();
                            sender.sendMessage(prefix + ChatColor.GREEN + arg + " " + ChatColor.WHITE + "removed from shadowban list");
                            return true;
                        }
                    } else {
                        sender.sendMessage(prefix + "You can't use this command.");
                        return true;
                    }
                } else {
                    debug("not enough args");
                    return false;
                }
            } else {
                if (args.length >= 1) {
                    for (String arg : args) {
                        playerlist.remove(arg);
                        getConfig().set("shadowbannedList", playerlist);
                        saveConfig();
                        sender.sendMessage(prefix + ChatColor.GREEN + arg + " " + ChatColor.WHITE + "removed from shadowban list");
                        return true;
                    }
                } else {
                    return false;
                }
            }
        } else if (command.getLabel().equalsIgnoreCase("shadowbanreload")) {
            if (sender.hasPermission("shadowban.reload")) {
                playerlist = getConfig().getStringList("shadowbannedList");
                sender.sendMessage(prefix + ChatColor.WHITE + "Config reloaded.");
                return true;
            } else {
                sender.sendMessage(prefix + "You can't use this command.");
                return true;
            }
        } else if (command.getLabel().equalsIgnoreCase("shadowbanlist")) {
            if (sender.hasPermission("shadowban.list")) {
                String list = ChatColor.GREEN + Joiner.on(ChatColor.WHITE + ", " + ChatColor.GREEN).join(playerlist);
                sender.sendMessage(prefix + list);
                return true;
            } else {
                sender.sendMessage(prefix + "You can't use this command.");
                return true;
            }
        }
        return false;
    }
}
