// 
// Decompiled by Procyon v0.5.30
// 

package com.Arceus02.RegionCommand;

public class ResetCooldownRunner implements Runnable
{
    private final RegionCmdPlayerListener listener;
    private String player;
    private Region.MovementType movementtype;
    
    public ResetCooldownRunner(final RegionCmdPlayerListener regionCmdPlayerListener, final String player, final Region.MovementType movementtype) {
        this.listener = regionCmdPlayerListener;
        this.player = player;
        this.movementtype = movementtype;
    }
    
    @Override
    public void run() {
        if (this.movementtype == Region.MovementType.ENTER) {
            this.listener.isPlayerCooldownedEnter.put(this.player, false);
        }
        if (this.movementtype == Region.MovementType.QUIT) {
            this.listener.isPlayerCooldownedQuit.put(this.player, false);
        }
    }
}
