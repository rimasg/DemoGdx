package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by SID on 2017-03-30.
 */

public final class AssetsNew {
    private static AssetsNew inst;
    private AssetManager am;

    private AssetsNew() {
        am = new AssetManager();
        setLoaders();
        load();
    }

    public static synchronized AssetsNew inst() {
        if (inst == null) {
            inst = new AssetsNew();
        }
        return inst;
    }

    private void setLoaders() {
        final InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        am.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        am.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        am.setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
    }

    private void load() {
        am.load(AssetDescriptors.SKIN);
        am.load(AssetDescriptors.TEXTURE_ATLAS);
        am.load(AssetDescriptors.FONT_FREE_MONO_BOLD_32);
        // TODO: 2017-03-31 add other fonts loading
        am.load(AssetDescriptors.SOUND_COLLISION);
        am.load(AssetDescriptors.MUSIC_BG);

        am.load(AssetDescriptors.PE_EXPLOSION);
        am.load(AssetDescriptors.PE_GRAVITY_EXPLOSION);
        am.load(AssetDescriptors.PE_STAR_TRAIL);
        am.load(AssetDescriptors.PE_SIMPLE_TRAIL);

        am.load(AssetDescriptors.MAP_HUNTER);
    }

    public Skin getSkin() {
        return am.get(AssetDescriptors.SKIN);
    }

    public TextureAtlas getAtlas() {
        return am.get(AssetDescriptors.TEXTURE_ATLAS);
    }

    public TextureAtlas.AtlasRegion getRegion(String regionName) {
        return am.get(AssetDescriptors.TEXTURE_ATLAS).findRegion(regionName);
    }

    public BitmapFont getFont(AssetDescriptor<BitmapFont> descriptor) {
        return am.get(descriptor);
    }

    public Sound getSound(AssetDescriptor<Sound> descriptor) {
        return am.get(descriptor);
    }

    public Music getMusic(AssetDescriptor<Music> descriptor) {
        return am.get(descriptor);
    }

    public ParticleEffect getParticleEffect(AssetDescriptor<ParticleEffect> descriptor) {
        return am.get(descriptor);
    }

    public TiledMap getTiledMap(AssetDescriptor<TiledMap> descriptor) {
        return am.get(descriptor);
    }

    public boolean update() {
        return am.update();
    }

    public void finishLoading() {
        am.finishLoading();
    }

    public float getProgress() {
        return am.getProgress();
    }

    public void dispose() {
        am.dispose();
    }
}
