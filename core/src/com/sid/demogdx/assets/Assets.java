package com.sid.demogdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Created by SID on 2017-03-30.
 */

public final class Assets {
    private static Assets inst;
    private AssetManager am;

    private Assets() {
        am = new AssetManager();
        setLoaders();
        load();
    }

    public static synchronized Assets inst() {
        if (inst == null) inst = new Assets();
        return inst;
    }

    private void setLoaders() {
        final InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        am.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        am.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        am.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    private void load() {
        am.load(AssetDescriptors.SKIN);
        am.load(AssetDescriptors.TEXTURE_ATLAS);

        am.load(AssetDescriptors.FONT_BLACK);
        am.load(AssetDescriptors.FONT_WHITE);
        am.load(AssetDescriptors.FONT_OPEN_SANS_REGULAR_26);

        am.load(AssetDescriptors.I18N);

        am.load(AssetDescriptors.SOUND_COLLISION);
        am.load(AssetDescriptors.MUSIC_BG);

        am.load(AssetDescriptors.PE_EXPLOSION);
        am.load(AssetDescriptors.PE_GRAVITY_EXPLOSION);
        am.load(AssetDescriptors.PE_STAR_TRAIL);
        am.load(AssetDescriptors.PE_SIMPLE_TRAIL);
        am.load(AssetDescriptors.PE_SIMPLE_TRAIL_BOX2D);
        am.load(AssetDescriptors.PE_DEFAULT_BOX2D);
        am.load(AssetDescriptors.PE_EXPLOSION_BOX2D);

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

    public I18NBundle getStrings() {
        return am.get(AssetDescriptors.I18N);
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

    /**
     * @return <code>true</code> when the assets will be loaded.
     * @see AssetManager#update()
     */
    public boolean update() {
        return am.update();
    }

    /**
     * Blocks until all assets are loaded.
     * @see AssetManager#finishLoading()
     */
    public void finishLoading() {
        am.finishLoading();
    }

    public float getProgress() {
        return am.getProgress();
    }

    public void dispose() {
        am.dispose();
        inst = null;
    }
}
