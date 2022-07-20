package com.elysian.client.event.events;

public class EventMotionUpdate extends EventCancellable {

    public int stage;

    public EventMotionUpdate(int stage) {
        super();
        this.stage = stage;
    }

    public int get_stage() {
        return this.stage;
    }
}