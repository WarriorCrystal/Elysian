package com.elysian.client.util.summit;

public class Summit {

    private static FontManager m_FontManager;

    public static FontManager GetFontManager() {
        return Summit.m_FontManager;
    }
    static {
        Summit.m_FontManager = new FontManager();
    }

}
