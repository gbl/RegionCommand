// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02;

import org.bukkit.World;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.io.Serializable;

public class Zone implements Serializable
{
    private static final long serialVersionUID = -5113918118329231272L;
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;
    private String world;
    
    public Zone(final Location loc1, final Location loc2) {
        this.minX = ((loc1.getX() < loc2.getX()) ? loc1.getX() : loc2.getX());
        this.minY = ((loc1.getY() < loc2.getY()) ? loc1.getY() : loc2.getY());
        this.minZ = ((loc1.getZ() < loc2.getZ()) ? loc1.getZ() : loc2.getZ());
        this.maxX = ((loc1.getX() > loc2.getX()) ? loc1.getX() : loc2.getX());
        this.maxY = ((loc1.getY() > loc2.getY()) ? loc1.getY() : loc2.getY());
        this.maxZ = ((loc1.getZ() > loc2.getZ()) ? loc1.getZ() : loc2.getZ());
        this.world = loc1.getWorld().getName();
    }
    
    public Zone(final Location loc1, final Location loc2, final String world) {
        this.minX = ((loc1.getX() < loc2.getX()) ? loc1.getX() : loc2.getX());
        this.minY = ((loc1.getY() < loc2.getY()) ? loc1.getY() : loc2.getY());
        this.minZ = ((loc1.getZ() < loc2.getZ()) ? loc1.getZ() : loc2.getZ());
        this.maxX = ((loc1.getX() > loc2.getX()) ? loc1.getX() : loc2.getX());
        this.maxY = ((loc1.getY() > loc2.getY()) ? loc1.getY() : loc2.getY());
        this.maxZ = ((loc1.getZ() > loc2.getZ()) ? loc1.getZ() : loc2.getZ());
        this.world = world;
    }
    
    public Location getMinLoc() {
        return new Location(Bukkit.getWorld(this.world), this.minX, this.minY, this.minZ);
    }
    
    public Location getMaxLoc() {
        return new Location(Bukkit.getWorld(this.world), this.maxX, this.maxY, this.maxZ);
    }
    
    public Boolean isIn(final Location location) {
        if (location.getX() >= this.minX && location.getX() <= this.maxX + 1.0 && location.getY() >= this.minY && location.getY() <= this.maxY + 1.0 && location.getZ() >= this.minZ && location.getZ() <= this.maxZ + 1.0) {
            return true;
        }
        return false;
    }
    
    public String getWorldName() {
        return this.world;
    }
    
    public ArrayList<Location> getAllLocations() {
        final ArrayList<Location> locs = new ArrayList<Location>();
        final World world = this.getMinLoc().getWorld();
        for (double x = this.getMinLoc().getX(); x <= this.getMaxLoc().getX(); ++x) {
            for (double y = this.getMinLoc().getY(); y <= this.getMaxLoc().getY(); ++y) {
                for (double z = this.getMinLoc().getZ(); z <= this.getMaxLoc().getZ(); ++z) {
                    final Location temploc = new Location(world, x, y, z);
                    locs.add(temploc);
                }
            }
        }
        return locs;
    }
}
