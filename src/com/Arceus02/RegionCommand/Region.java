// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02.RegionCommand;

import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.ArrayList;
import com.Arceus02.Zone;

public class Region
{
    private Zone zone;
    private String idname;
    private ArrayList<String> commands_enter;
    private ArrayList<String> commands_quit;
    private boolean forceop;
    private Integer cooldown_enter;
    private Integer cooldown_quit;
    
    public Region(final Zone zone, final String idname, final ArrayList<String> commands_enter, final ArrayList<String> commands_quit, final boolean forceop, final Integer cooldown_enter, final Integer cooldown_quit) {
        this.zone = zone;
        this.idname = idname;
        this.commands_enter = commands_enter;
        this.commands_quit = commands_quit;
        this.forceop = forceop;
        this.cooldown_enter = cooldown_enter;
        this.cooldown_quit = cooldown_quit;
    }
    
    public boolean isIn(final Location loc) {
        return this.zone.isIn(loc);
    }
    
    public String getName() {
        return this.idname;
    }
    
    public ArrayList<String> getCommandsEnter() {
        return this.commands_enter;
    }
    
    public ArrayList<String> getCommandsQuit() {
        return this.commands_quit;
    }
    
    public boolean isForceOp() {
        return this.forceop;
    }
    
    public Integer getCooldownEnter() {
        return this.cooldown_enter;
    }
    
    public Integer getCooldownQuit() {
        return this.cooldown_quit;
    }
    
    public void performCommands(final Player player, final MovementType movementtype) {
        final boolean isOp = player.isOp();
        if (this.isForceOp()) {
            player.setOp(true);
        }
        final ArrayList<String> commands = (movementtype == MovementType.ENTER) ? this.commands_enter : this.commands_quit;
        for (final String command : commands) {
            System.out.println("RegionCmd : (" + player.getName() + ") Performing command : " + command);
            player.performCommand(command);
        }
        if (this.isForceOp() && !isOp) {
            player.setOp(false);
        }
    }
    
    @Override
    public String toString() {
        return this.idname;
    }
    
    enum MovementType
    {
        ENTER("ENTER", 0), 
        QUIT("QUIT", 1);
        
        private MovementType(final String s, final int n) {
        }
    }
}
