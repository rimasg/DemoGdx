package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by SID on 2017-01-18.
 */

public final class AssetsNew implements Disposable {

    private static AssetManager am;

    static {
        am = new AssetManager(); load();
    }

    private static synchronized void load() {
        // TODO: 2017-01-18 implement code
    }

    public static boolean update() {
        return am.update();
    }

    public static void finishLoading() {
        am.finishLoading();
    }

    private AssetsNew() { }

    @Override
    public void dispose() {
        am.dispose();
    }
}
