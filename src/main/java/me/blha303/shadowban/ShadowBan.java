package me.blha303.shadowban;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

//import com.ensifera.animosity.craftirc.CraftIRC;
import com.google.common.base.Joiner;

public class ShadowBan extends JavaPlugin implements Listener {

    Set<String> playerSet = Collections.synchronizedSet(new HashSet<String>());
    boolean debug;
//    private CraftIRC craftirc = null;

    public void onEnable() {
        saveDefaultConfig();
        debug = getConfig().getBoolean("debug");
        getServer().getPluginManager().registerEvents(this, this);
        debug("Events registered");
        playerSet.addAll(getConfig().getStringList("shadowbannedList"));
//        if (getServer().getPluginManager().getPlugin("CraftIRC") != null) {
//            craftirc = (CraftIRC) getServer().getPluginManager().getPlugin("CraftIRC");
//        }
    }

    public void onDisable() {
        saveConfig();
    }

    public void debug(String msg) {
        if (debug)
            this.getLogger().info("DEBUG: " + msg);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        debug("Entered PlayerChatEvent");
//        debug("Trying to cancel sending to CraftIRC");
//        if (craftirc != null) {
//            event.getHandlers().unregister(craftirc);
//        }
        debug("Wonder if that worked.");
        if (playerSet.contains(event.getPlayer().getName())) {
            debug("Playerlist contains this name!");
            event.getRecipients().clear();
            debug("List cleared");
            event.getRecipients().add(event.getPlayer());
            debug("Recipient added");
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
                                playerSet.add(getServer().getPlayer(arg).getName());
                                debug("arg added");
                            } else {
                                debug("couldn't find player from arg");
                                sender.sendMessage(prefix + ChatColor.WHITE + "Could not find player \"" + ChatColor.RED + arg + ChatColor.WHITE + "\"");
                                return true;
                            }
                            getConfig().set("shadowbannedList", Arrays.asList(playerSet.toArray(new String[playerSet.size()])));
                            saveConfig();
                            sender.sendMessage(prefix + ChatColor.GREEN + getServer().getPlayer(arg).getName() + " " + ChatColor.WHITE + "added to shadowban list");
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
                            playerSet.add(getServer().getPlayer(arg).getName());
                        } else {
                            sender.sendMessage(prefix + ChatColor.WHITE + "Could not find player \"" + ChatColor.RED + arg + ChatColor.WHITE + "\"");
                            return true;
                        }
                        getConfig().set("shadowbannedList", Arrays.asList(playerSet.toArray(new String[playerSet.size()])));
                        saveConfig();
                        sender.sendMessage(prefix + ChatColor.GREEN + getServer().getPlayer(arg).getName() + " " + ChatColor.WHITE + "added to shadowban list");
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
                            playerSet.add(arg);
                            debug("arg added");
                            getConfig().set("shadowbannedList", Arrays.asList(playerSet.toArray(new String[playerSet.size()])));
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
                        playerSet.add(arg);
                        getConfig().set("shadowbannedList", Arrays.asList(playerSet.toArray(new String[playerSet.size()])));
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
                            playerSet.remove(arg);
                            debug("arg removed");
                            getConfig().set("shadowbannedList", Arrays.asList(playerSet.toArray(new String[playerSet.size()])));
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
                        playerSet.remove(arg);
                        getConfig().set("shadowbannedList", Arrays.asList(playerSet.toArray(new String[playerSet.size()])));
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
                playerSet.clear();
                playerSet.addAll(getConfig().getStringList("shadowbannedList"));
                sender.sendMessage(prefix + ChatColor.WHITE + "Config reloaded.");
                return true;
            } else {
                sender.sendMessage(prefix + "You can't use this command.");
                return true;
            }
        } else if (command.getLabel().equalsIgnoreCase("shadowbanlist")) {
            if (sender.hasPermission("shadowban.list")) {
                String list = ChatColor.GREEN + Joiner.on(ChatColor.WHITE + ", " + ChatColor.GREEN).join(playerSet);
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
