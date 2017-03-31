package com.sid.demogdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.sid.demogdx.assets.FontNames;

/**
 * Created by Okis on 2016.11.09.
 */

@Deprecated
public final class FontsFactory {
    public static BitmapFont createFont(String fontName, FontNames.FontSize size) {
        return createFont(fontName, size, Color.WHITE);
    }

    public static BitmapFont createFont(String fontName, FontNames.FontSize size, Color color) {
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = size.size();
        param.color = color;
        final BitmapFont font = generator.generateFont(param);
        generator.dispose();
        return font;
    }

    private FontsFactory() { }
}