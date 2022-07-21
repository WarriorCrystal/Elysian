package com.elysian.client.util.summit;

import org.lwjgl.opengl.GL11;

public class CachedFont
{
    private final int displayList;
    private long lastUsage;
    private boolean deleted;
    
    public CachedFont(final int displayList, final long lastUsage, final boolean deleted) {
        this.deleted = false;
        this.displayList = displayList;
        this.lastUsage = lastUsage;
        this.deleted = deleted;
    }
    
    public CachedFont(final int displayList, final long lastUsage) {
        deleted = false;
        this.displayList = displayList;
        this.lastUsage = lastUsage;
    }
    
    @Override
    protected void finalize() {
        if (!deleted) {
            GL11.glDeleteLists(displayList, 1);
        }
    }
    
    public int getDisplayList() {
        return displayList;
    }
    
    public long getLastUsage() {
        return lastUsage;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setLastUsage(final long lastUsage) {
        this.lastUsage = lastUsage;
    }
    
    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }
}
