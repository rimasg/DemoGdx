package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

    private static final ParticleEffectLoader.ParticleEffectParameter PE_PARAMS = new ParticleEffectLoader.ParticleEffectParameter();
    private static final SkinLoader.SkinParameter SKIN_PARAMS = new SkinLoader.SkinParameter(AssetPaths.TEXTURE_ATLAS_FILE);

    public static final AssetDescriptor<Skin> SKIN = new
            AssetDescriptor<>(AssetPaths.SKIN_JSON, SKIN_CLASS, SKIN_PARAMS);
    // TODO: 2017.01.18 finish implementation
    //

    public static final AssetDescriptor<Sound> SOUND_COLLISION = new
            AssetDescriptor<>(AssetPaths.SOUND_COLLISION, SOUND_CLASS);

    public static final AssetDescriptor<ParticleEffect> PE_EXPLOSION = new
            AssetDescriptor<>(AssetPaths.PE_EXPLOSION, PE_CLASS, PE_PARAMS);
    public static final AssetDescriptor<ParticleEffect> PE_GRAVITY_EXPLOSION = new
            AssetDescriptor<>(AssetPaths.PE_GRAVITY_EXPLOSION, PE_CLASS, PE_PARAMS);
    public static final AssetDescriptor<ParticleEffect> PE_STAR_TRAIL = new
            AssetDescriptor<>(AssetPaths.PE_STAR_TRAIL, PE_CLASS, PE_PARAMS);
    public static final AssetDescriptor<ParticleEffect> PE_SIMPLE_TRAIL = new
            AssetDescriptor<>(AssetPaths.PE_SIMPLE_TRAIL, PE_CLASS, PE_PARAMS);

    static {
        PE_PARAMS.atlasFile = AssetPaths.TEXTURE_ATLAS_FILE;
    }

    private AssetDescriptors() { }
}
