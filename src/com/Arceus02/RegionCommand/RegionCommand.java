// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02.RegionCommand;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Iterator;
import com.Arceus02.Zone;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;

public class RegionCommand extends JavaPlugin
{
    private ArrayList<Region> regions;
    
    public RegionCommand() {
        this.regions = new ArrayList<Region>();
    }
    
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener)new RegionPlayerCallerListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new RegionCmdPlayerListener(this), (Plugin)this);
        this.reloadRegions();
    }
    
    public void onDisable() {
    }
    
    public void reloadRegions() {
        this.reloadConfig();
        this.getLogger().info("Loading regions : " + this.getConfig().getKeys(false).toString());
        this.regions = new ArrayList<Region>();
        for (final String i : this.getConfig().getKeys(false)) {
            final ConfigurationSection configregion = this.getConfig().getConfigurationSection(i);
            final String idname = i;
            final String worldname = configregion.getString("world");
            final Location loc1 = configregion.getVector("loc1").toLocation(this.getServer().getWorld(worldname));
            final Location loc2 = configregion.getVector("loc2").toLocation(this.getServer().getWorld(worldname));
            if (loc1 != null && loc2 != null) {
                final Zone zone = new Zone(loc1, loc2);
                final ArrayList<String> commands_enter = (ArrayList<String>)configregion.getStringList("commands_enter");
                final ArrayList<String> command_quit = (ArrayList<String>)configregion.getStringList("commands_quit");
                final Boolean forceOp = configregion.getBoolean("forceop");
                final Integer cooldown_enter = configregion.getInt("cooldown_enter");
                final Integer cooldown_quit = configregion.getInt("cooldown_quit");
                final Region region = new Region(zone, idname, commands_enter, command_quit, forceOp, cooldown_enter, cooldown_quit);
                this.regions.add(region);
            }
            else {
                this.getLogger().warning("Could not load region : " + idname + "(the region's locations are invalid)");
            }
        }
        this.getLogger().info(String.valueOf(this.regions.size()) + " regions loaded : " + this.regions.toString());
    }
    
    public Region getRegion(final Location location) {
        for (final Region tempregion : this.regions) {
            if (tempregion.isIn(location)) {
                return tempregion;
            }
        }
        return null;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("createregion")) {
            if (args.length < 1) {
                sender.sendMessage("/createregion <idname>");
                return true;
            }
            for (final Region i : this.regions) {
                if (i.getName().equalsIgnoreCase(args[0])) {
                    sender.sendMessage("Region already exists with this idname : " + args[0]);
                    return true;
                }
            }
            Location loc1 = new Location((World)this.getServer().getWorlds().get(0), 0.0, 0.0, 0.0);
            Location loc2 = new Location((World)this.getServer().getWorlds().get(0), 0.0, 0.0, 0.0);
            if (sender instanceof Player) {
                final WorldEditPlugin wep = (WorldEditPlugin)this.getServer().getPluginManager().getPlugin("WorldEdit");
                final WorldEdit we = wep.getWorldEdit();
                final Player p = (Player)sender;
                final LocalSession s = we.getSession(p.getName());
                try {
                    com.sk89q.worldedit.regions.Region region = null;
                    if (s != null && s.getSelectionWorld() != null) {
                        region = s.getSelection(s.getSelectionWorld());
                    }
                    if (region != null) {
                        final World world = this.getServer().getWorld(s.getSelectionWorld().getName());
                        loc1 = new Location(world, region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
                        loc2 = new Location(world, region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());
                    }
                }
                catch (Exception ex) {}
            }
            this.reloadConfig();
            this.getConfig().createSection(args[0]);
            this.getConfig().getConfigurationSection(args[0]).set("world", (Object)loc1.getWorld().getName());
            this.getConfig().getConfigurationSection(args[0]).set("loc1", (Object)loc1.toVector());
            this.getConfig().getConfigurationSection(args[0]).set("loc2", (Object)loc2.toVector());
            this.getConfig().getConfigurationSection(args[0]).set("commands_enter", (Object)new ArrayList());
            this.getConfig().getConfigurationSection(args[0]).set("commands_quit", (Object)new ArrayList());
            this.getConfig().getConfigurationSection(args[0]).set("forceop", (Object)true);
            this.getConfig().getConfigurationSection(args[0]).set("cooldown_enter", (Object)0);
            this.getConfig().getConfigurationSection(args[0]).set("cooldown_quit", (Object)0);
            this.saveConfig();
            sender.sendMessage("Region created (" + args[0] + "), check config file to modify its properties.");
        }
        if (command.getName().equalsIgnoreCase("removeregion")) {
            if (args.length < 1) {
                sender.sendMessage("/removeregion <idname>");
                return true;
            }
            for (final Region i : this.regions) {
                if (i.getName().equalsIgnoreCase(args[0])) {
                    this.regions.remove(i);
                    this.reloadConfig();
                    this.getConfig().set(args[0], (Object)null);
                    this.saveConfig();
                    sender.sendMessage("Region deleted : " + args[0]);
                    return true;
                }
            }
            return true;
        }
        else {
            if (command.getName().equalsIgnoreCase("reloadregion")) {
                this.reloadRegions();
                sender.sendMessage("Regions reloaded : " + this.regions.toString());
                return true;
            }
            return false;
        }
    }
}
