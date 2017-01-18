package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
        inst.load(AssetDescriptors.SKIN);
        inst.load(AssetDescriptors.SOUND_COLLISION);
        inst.load(AssetDescriptors.PARTICLE_EFFECT_EXPLOSION);
        inst.load(AssetDescriptors.PARTICLE_EFFECT_GRAVITY_EXPLOSION);
        inst.load(AssetDescriptors.PARTICLE_EFFECT_TRAIL);
    }

    public static Skin getSkin() {
        return inst.get(AssetDescriptors.SKIN);
    }

    public static Sound getSound(AssetDescriptor<Sound> descriptor) {
        return inst.get(descriptor);
    }

    public static ParticleEffect getParticleEffect(AssetDescriptor<ParticleEffect> descriptor) {
        return inst.get(descriptor);
    }

    @Override
    public synchronized void dispose() {
        super.dispose();
        inst = null;
    }
}
