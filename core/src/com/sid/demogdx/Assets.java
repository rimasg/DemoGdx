package com.sid.demogdx;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Okis on 2016.03.04 @ 21:52.
 */
public class Assets extends AssetManager {
    private static Assets instance;

    private Assets() { }

    public static Assets inst() {
        if (instance == null) instance = new Assets();
        return instance;
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
        instance = null;
    }
}
