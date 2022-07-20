/*
 * Decompiled with CFR 0.151.
 */
package com.elysian.client.turok.render.font;

import com.elysian.client.turok.render.font.hal.CFontRenderer;

import java.awt.*;

public class TurokFont
        extends CFontRenderer {
    private final Font font;
    private boolean isRenderingCustomFont;

    public TurokFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.font = font;
        this.isRenderingCustomFont = true;
    }

    public void setRenderingCustomFont(boolean renderingCustomFont) {
        this.isRenderingCustomFont = renderingCustomFont;
    }

    public boolean isRenderingCustomFont() {
        return this.isRenderingCustomFont;
    }
}

