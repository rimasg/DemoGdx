package com.sid.demogdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    public static Assets inst() {
        if (inst == null) {
            inst = new Assets();
            loadAssets();
        }
        return inst;
    }

    private static void loadAssets() {
        inst.load("skin.json", Skin.class, new SkinLoader.SkinParameter("textures/texture.pack"));
        inst.load(COLLISION_SOUND, SOUND_CLASS);
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
        inst = null;
    }

    public static final String COLLISION_SOUND = "sounds/click.ogg";

    public static final Class<Sound> SOUND_CLASS = Sound.class;
    public static final Class<Music> MUSIC_CLASS = Music.class;
}
