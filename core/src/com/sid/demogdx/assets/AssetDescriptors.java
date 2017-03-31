package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.sid.demogdx.assets.FontNames.*;

/**
 * Created by SID on 2016-12-01.
 */

public final class AssetDescriptors {
    private static final Class<Skin> SKIN_CLASS = Skin.class;
    private static final Class<TextureAtlas> TEXTURE_ATLAS_CLASS = TextureAtlas.class;
    private static final Class<BitmapFont> BITMAP_FONT_CLASS = BitmapFont.class;
    private static final Class<Sound> SOUND_CLASS = Sound.class;
    private static final Class<Music> MUSIC_CLASS = Music.class;
    private static final Class<ParticleEffect> PE_CLASS = ParticleEffect.class;
    private static final Class<TiledMap> TILED_MAP_CLASS = TiledMap.class;

    private static final ParticleEffectLoader.ParticleEffectParameter PE_PARAMS = new ParticleEffectLoader.ParticleEffectParameter();
    public static final FreetypeFontLoader.FreeTypeFontLoaderParameter FONT_PARAM_FREE_MONO_BOLD_32
            = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

    public static final AssetDescriptor<Skin> SKIN = new
            AssetDescriptor<>(AssetPaths.SKIN_JSON, SKIN_CLASS);
    public static final AssetDescriptor<TextureAtlas> TEXTURE_ATLAS = new
            AssetDescriptor<>(AssetPaths.TEXTURE_ATLAS, TEXTURE_ATLAS_CLASS);

    public static final AssetDescriptor<Sound> SOUND_COLLISION = new
            AssetDescriptor<>(AssetPaths.SOUND_COLLISION, SOUND_CLASS);
    public static final AssetDescriptor<Music> MUSIC_BG = new AssetDescriptor<>
            (AssetPaths.MUSIC_BG, MUSIC_CLASS);

    public static final AssetDescriptor<ParticleEffect> PE_EXPLOSION = new
            AssetDescriptor<>(AssetPaths.PE_EXPLOSION, PE_CLASS, PE_PARAMS);
    public static final AssetDescriptor<ParticleEffect> PE_GRAVITY_EXPLOSION = new
            AssetDescriptor<>(AssetPaths.PE_GRAVITY_EXPLOSION, PE_CLASS, PE_PARAMS);
    public static final AssetDescriptor<ParticleEffect> PE_STAR_TRAIL = new
            AssetDescriptor<>(AssetPaths.PE_STAR_TRAIL, PE_CLASS, PE_PARAMS);
    public static final AssetDescriptor<ParticleEffect> PE_SIMPLE_TRAIL = new
            AssetDescriptor<>(AssetPaths.PE_SIMPLE_TRAIL, PE_CLASS, PE_PARAMS);

    public static final AssetDescriptor<TiledMap> MAP_HUNTER = new
            AssetDescriptor<>(AssetPaths.MAP_HUNTER, TILED_MAP_CLASS);

    public static final AssetDescriptor<BitmapFont> FONT_BLACK = new AssetDescriptor<>
            (AssetPaths.FONT_BLACK, BITMAP_FONT_CLASS);
    public static final AssetDescriptor<BitmapFont> FONT_WHITE = new AssetDescriptor<>
            (AssetPaths.FONT_WHITE, BITMAP_FONT_CLASS);
    public static final AssetDescriptor<BitmapFont> FONT_FREE_MONO_BOLD_32 = new
            AssetDescriptor<>(AssetPaths.FONT_FREE_MONO_BOLD, BITMAP_FONT_CLASS, FONT_PARAM_FREE_MONO_BOLD_32);

    static {
        PE_PARAMS.atlasFile = AssetPaths.TEXTURE_ATLAS;

        FONT_PARAM_FREE_MONO_BOLD_32.fontFileName = AssetPaths.FONT_FREE_MONO_BOLD;
        FONT_PARAM_FREE_MONO_BOLD_32.fontParameters.size = FontSize.SIZE_32.size();


    }

    private AssetDescriptors() { }
}
