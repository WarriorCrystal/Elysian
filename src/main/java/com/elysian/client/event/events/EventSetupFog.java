package com.elysian.client.event.events;

public class EventSetupFog extends EventCancellable{

    public int start_coords;
    public float partial_ticks;

    public EventSetupFog(int coords, float ticks) {
        start_coords = coords;
        partial_ticks = ticks;
    }

}

