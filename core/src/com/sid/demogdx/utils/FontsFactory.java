package com.sid.demogdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Okis on 2016.11.09.
 */

public final class FontsFactory {

    /**
     * Default font color {@link Color#WHITE}
     * @param fontName Font name {@link FontName}
     * @param size Font size {@link FontSize}
     * @return {@link BitmapFont}
     */
    public static BitmapFont createFont(FontsFactory.FontName fontName, FontsFactory.FontSize size) {
        return createFont(fontName, size, Color.WHITE);
    }

    public static BitmapFont createFont(FontName fontName, FontSize size, Color color) {
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName.fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = size.size;
        param.color = color;
        final BitmapFont font = generator.generateFont(param);
        generator.dispose();
        return font;
    }

    public enum FontSize {
        SIZE_32(32), SIZE_48(48), SIZE_64(64);
        private int size;

        FontSize(int size) {
            this.size = size;
        }
    }

    public enum FontName {
        FREE_MONO_BOLD("fonts/FreeMonoBold.ttf");
        private String fontName;

        FontName(String fontName) {
            this.fontName = fontName;
        }
    }
}