package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by SID on 2017-01-18.
 */

public final class AssetsNew {

    private static final String TAG = "Assets";
    private static AssetManager am;

    static {
        am = new AssetManager(); load();
    }

    private AssetsNew() { }

    private static void load() {
        am.load(AssetDescriptors.SKIN);
        am.load(AssetDescriptors.SOUND_COLLISION);
        am.load(AssetDescriptors.PARTICLE_EFFECT_EXPLOSION);
        am.load(AssetDescriptors.PARTICLE_EFFECT_GRAVITY_EXPLOSION);
        am.load(AssetDescriptors.PARTICLE_EFFECT_TRAIL);
    }

    public static Skin getSkin() {
        return am.get(AssetDescriptors.SKIN);
    }

    public static Sound getSound(AssetDescriptor<Sound> descriptor) {
        return am.get(descriptor);
    }

    public static ParticleEffect getParticleEffect(AssetDescriptor<ParticleEffect> descriptor) {
        return am.get(descriptor);
    }

    public static boolean update() {
        return am.update();
    }

    public static void finishLoading() {
        am.finishLoading();
    }

    public static void dispose() {
        am.dispose();
    }
}
