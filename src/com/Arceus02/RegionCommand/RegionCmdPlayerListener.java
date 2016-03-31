// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02.RegionCommand;

import com.Arceus02.RegionCommand.Event.PlayerLeaveRegionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import com.Arceus02.RegionCommand.Event.PlayerEnterRegionEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.Listener;

public class RegionCmdPlayerListener implements Listener
{
    private final RegionCommand plugin;
    public Map<String, Boolean> isPlayerCooldownedEnter;
    public Map<String, Boolean> isPlayerCooldownedQuit;
    
    public RegionCmdPlayerListener(final RegionCommand plugin) {
        this.plugin = plugin;
        this.isPlayerCooldownedEnter = new HashMap<String, Boolean>();
        this.isPlayerCooldownedQuit = new HashMap<String, Boolean>();
    }
    
    @EventHandler
    public void onPlayerEnterRegion(final PlayerEnterRegionEvent event) {
        try {
            final String player = event.getPlayer().getName();
            if (!this.isPlayerCooldownedEnter.containsKey(player)) {
                this.isPlayerCooldownedEnter.put(event.getPlayer().getName(), false);
            }
            if (!this.isPlayerCooldownedEnter.get(event.getPlayer().getName())) {
                event.getRegion().performCommands(event.getPlayer(), Region.MovementType.ENTER);
                if (event.getRegion().getCooldownEnter() > 0) {
                    this.isPlayerCooldownedEnter.put(player, true);
                    final long time = event.getRegion().getCooldownEnter() * 20;
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new ResetCooldownRunner(this, player, Region.MovementType.ENTER), time);
                }
            }
        }
        catch (Exception e) {
            Bukkit.getLogger().warning("RegionCommand error (onPlayerEnterRegion) : " + e.getMessage());
        }
    }
    
    @EventHandler
    public void onPlayerQuitRegion(final PlayerLeaveRegionEvent event) {
        try {
            final String player = event.getPlayer().getName();
            if (!this.isPlayerCooldownedQuit.containsKey(player)) {
                this.isPlayerCooldownedQuit.put(event.getPlayer().getName(), false);
            }
            if (!this.isPlayerCooldownedQuit.get(event.getPlayer().getName())) {
                event.getRegion().performCommands(event.getPlayer(), Region.MovementType.QUIT);
                if (event.getRegion().getCooldownQuit() > 0) {
                    this.isPlayerCooldownedQuit.put(player, true);
                    final long time = event.getRegion().getCooldownQuit() * 20;
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new ResetCooldownRunner(this, player, Region.MovementType.QUIT), time);
                }
            }
        }
        catch (Exception e) {
            Bukkit.getLogger().warning("RegionCommand error (onPlayerQuitRegion) : " + e.getMessage());
        }
    }
}
