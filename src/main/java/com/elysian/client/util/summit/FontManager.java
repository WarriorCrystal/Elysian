package com.elysian.client.util.summit;

import java.io.InputStream;
import java.awt.Font;

public class FontManager
{
    public GameFontRenderer badaboom;
    public GameFontRenderer lato;
    public GameFontRenderer lato80;
    
    public FontManager() {
        badaboom = null;
        lato = null;
        lato80 = null;
    }
    
    public void Load() {
        try {
            badaboom = new GameFontRenderer(getFont("badaboom.ttf", 40.0f));
            lato = new GameFontRenderer(getFont("Lato-Medium.ttf", 40.0f));
            lato80 = new GameFontRenderer(getFont("Lato-Medium.ttf", 80.0f));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public GameFontRenderer getGameFont() {
        return lato;
    }
    
    private static Font getFont(final String fontName, final float size) {
        try {
            final InputStream inputStream = FontManager.class.getResourceAsStream("/assets/elysian/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            inputStream.close();
            return awtClientFont;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, (int)size);
        }
    }
    
    public void LoadCustomFont(final String customFont) {
        lato = new GameFontRenderer(new Font(customFont, 0, 19));
    }
    
    public float GetStringHeight(final String p_Name) {
        return (float)getGameFont().getHeight();
    }
    
    public float GetStringWidth(final String p_Name) {
        return (float)getGameFont().getStringWidth(p_Name);
    }
    
    public static FontManager Get() {
        return Summit.GetFontManager();
    }
}
