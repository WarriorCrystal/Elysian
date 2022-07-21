package com.elysian.client.util.summit;

public final class Timer
{
    private long time;
    
    public Timer() {
        time = -1L;
    }
    
    public boolean passed(final double ms) {
        return System.currentTimeMillis() - time >= ms;
    }
    
    public void reset() {
        time = System.currentTimeMillis();
    }
    
    public void resetTimeSkipTo(final long p_MS) {
        time = System.currentTimeMillis() + p_MS;
    }
    
    public long getTime() {
        return time;
    }
    
    public void setTime(final long time) {
        this.time = time;
    }
}
