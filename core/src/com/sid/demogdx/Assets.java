package com.sid.demogdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Okis on 2016.03.04 @ 21:52.
 */
public final class Assets extends AssetManager {
    private static final String TAG = "Assets";

    public static final String COLLISION_SOUND = "sounds/click.ogg";

    private static Assets instance;

    private Assets() { }

    public static Assets inst() {
        if (instance == null) {
            instance = new Assets();
            loadAssets();
        }
        return instance;
    }

    private static void loadAssets() {
        instance.load(COLLISION_SOUND, Sound.class);
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
        instance = null;
    }
}
