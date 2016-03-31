// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02.RegionCommand;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import com.Arceus02.RegionCommand.Event.PlayerLeaveRegionEvent;
import org.bukkit.event.Event;
import com.Arceus02.RegionCommand.Event.PlayerEnterRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.Listener;

public class RegionPlayerCallerListener implements Listener
{
    private final RegionCommand plugin;
    private Map<String, Boolean> playerInRegion;
    
    public RegionPlayerCallerListener(final RegionCommand regionCommand) {
        this.plugin = regionCommand;
        (this.playerInRegion = new HashMap<String, Boolean>()).clear();
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final String player = event.getPlayer().getName();
        if (!this.playerInRegion.containsKey(player)) {
            this.playerInRegion.put(player, false);
        }
        if (this.isEnteringInRegion(player, event.getTo())) {
            final Region region = this.plugin.getRegion(event.getTo());
            Bukkit.getPluginManager().callEvent((Event)new PlayerEnterRegionEvent(region, event.getPlayer()));
            this.playerInRegion.put(player, true);
        }
        else if (this.isLeavingRegion(player, event.getTo())) {
            final Region region = this.plugin.getRegion(event.getFrom());
            Bukkit.getPluginManager().callEvent((Event)new PlayerLeaveRegionEvent(region, event.getPlayer()));
            this.playerInRegion.put(player, false);
        }
    }
    
    private boolean isEnteringInRegion(final String player, final Location loc) {
        return !this.playerInRegion.get(player) && this.plugin.getRegion(loc) != null;
    }
    
    private boolean isLeavingRegion(final String player, final Location loc) {
        return this.playerInRegion.get(player) && this.plugin.getRegion(loc) == null;
    }
}
