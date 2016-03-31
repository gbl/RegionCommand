// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02.RegionCommand.Event;

import org.bukkit.entity.Player;
import com.Arceus02.RegionCommand.Region;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class PlayerLeaveRegionEvent extends Event
{
    private static final HandlerList handlers;
    private final Region region;
    private final Player player;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerLeaveRegionEvent(final Region region, final Player player) {
        this.region = region;
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Region getRegion() {
        return this.region;
    }
    
    public HandlerList getHandlers() {
        return PlayerLeaveRegionEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerLeaveRegionEvent.handlers;
    }
}
