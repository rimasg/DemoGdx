package com.sid.demogdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Okis on 2016.03.04 @ 21:52.
 */
public final class Assets extends AssetManager {
    private static final String TAG = "Assets";

    private static Assets inst;

    private Assets() { }

    /**
     * Instantiate Assets and load assets if @Assets instance is null
     * @return instance
     */
    public static synchronized Assets inst() {
        if (inst == null) {
            inst = new Assets();
            loadAssets();
        }
        return inst;
    }

    private static void loadAssets() {
        inst.load(SKIN_JSON, SKIN_CLASS, new SkinLoader.SkinParameter("textures/texture.pack"));
        inst.load(COLLISION_SOUND, SOUND_CLASS);
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
        inst = null;
    }

    public static Sound getCollisionSound() {
        return inst.get(Assets.COLLISION_SOUND, Assets.SOUND_CLASS);
    }

    private static final Class<Skin> SKIN_CLASS = Skin.class;
    private static final Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    private static final Class<BitmapFont> BITMAP_FONT_CLASS = BitmapFont.class;
    private static final Class<Sound> SOUND_CLASS = Sound.class;
    private static final Class<Music> MUSIC_CLASS = Music.class;

    private static final String SKIN_JSON = "skin.json";
    private static final String COLLISION_SOUND = "sounds/click.ogg";
}
