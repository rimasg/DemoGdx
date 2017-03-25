package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by SID on 2017-01-18.
 */

public final class Assets {
    private static final String TAG = "Assets";
    private static AssetManager am;

    static {
        am = new AssetManager();
        setLoaders();
        load();
    }

    private Assets() { }

    private static void setLoaders() {
        am.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
    }

    private static void load() {
        am.load(AssetDescriptors.SKIN);
        am.load(AssetDescriptors.TEXTURE_ATLAS);
        am.load(AssetDescriptors.SOUND_COLLISION);
        am.load(AssetDescriptors.PE_EXPLOSION);
        am.load(AssetDescriptors.PE_GRAVITY_EXPLOSION);
        am.load(AssetDescriptors.PE_STAR_TRAIL);
        am.load(AssetDescriptors.PE_SIMPLE_TRAIL);
        am.load(AssetDescriptors.MAP_HUNTER);
    }

    public static Skin getSkin() {
        return am.get(AssetDescriptors.SKIN);
    }

    public static TextureAtlas getAtlas() {
        return am.get(AssetDescriptors.TEXTURE_ATLAS);
    }

    public static TextureAtlas.AtlasRegion getRegion(String regionName) {
        return am.get(AssetDescriptors.TEXTURE_ATLAS).findRegion(regionName);
    }

    public static Sound getSound(AssetDescriptor<Sound> descriptor) {
        return am.get(descriptor);
    }

    public static Music getMusic(AssetDescriptor<Music> descriptor) {
        return am.get(descriptor);
    }

    public static ParticleEffect getParticleEffect(AssetDescriptor<ParticleEffect> descriptor) {
        return am.get(descriptor);
    }

    public static TiledMap getTiledMap(AssetDescriptor<TiledMap> descriptor) {
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
