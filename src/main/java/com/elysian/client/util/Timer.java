package com.elysian.client.util;

public class Timer {
    private long time;

    public Timer() {
        this.time = -1L;
    }

    public boolean getPassedMillis(final long ms) {
        return this.getPassedNanos(this.convertToNano(ms));
    }

    public boolean getPassedNanos(final long ns) {
        return System.nanoTime() - this.time >= ns;
    }

    public long convertToNano(final long time) {
        return time * 1000000L;
    }
}