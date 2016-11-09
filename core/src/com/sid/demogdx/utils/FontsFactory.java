package com.sid.demogdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Okis on 2016.11.09.
 */

public class FontsFactory {

    public static BitmapFont createFont(FontName fontName, FontSize size) {
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName.fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = size.size;
        final BitmapFont font = generator.generateFont(param);
        generator.dispose();
        return font;
    }

    public enum FontSize {
        Size32(32), Size48(48), Size64(64);
        private int size;

        FontSize(int size) {
            this.size = size;
        }
    }

    public enum FontName {
        FreeMonoBold ("fonts/FreeMonoBold.ttf");
        private String fontName;

        FontName(String fontName) {
            this.fontName = fontName;
        }
    }
}
