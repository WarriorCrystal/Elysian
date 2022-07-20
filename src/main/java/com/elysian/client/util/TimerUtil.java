//Decompiled by Procyon!

package com.elysian.client.util;

public class TimerUtil
{
    private long current;
    
    public TimerUtil() {
        this.current = System.currentTimeMillis();
    }
    
    public boolean hasReached(final long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }
    
    public boolean hasReached(final long delay, final boolean reset) {
        if (reset) {
            this.reset();
        }
        return System.currentTimeMillis() - this.current >= delay;
    }
    
    public void reset() {
        this.current = System.currentTimeMillis();
    }
    
    public long getTimePassed() {
        return System.currentTimeMillis() - this.current;
    }
}
