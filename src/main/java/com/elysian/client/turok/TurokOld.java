package com.elysian.client.turok;

// Draw.

import com.elysian.client.turok.draw.GL;
import com.elysian.client.turok.task.Font;

// Task.

/**
 * @author me
 * <p>
 * Created by me.
 * 27/04/20.
 */
public class TurokOld {
    private final String tag;

    private Font font_manager;

    public TurokOld(String tag) {
        this.tag = tag;
    }

    public void resize(int x, int y, float size) {
        GL.resize(x, y, size);
    }

    public void resize(int x, int y, float size, String tag) {
        GL.resize(x, y, size, "end");
    }

    public Font get_font_manager() {
        return this.font_manager;
    }
}