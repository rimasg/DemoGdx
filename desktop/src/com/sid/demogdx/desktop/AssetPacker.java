package com.sid.demogdx.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by SID on 2016-12-01.
 */

public class AssetPacker {
    private static final String RAW_ASSET_PATH = "desktop/assets-raw/textures";
    private static final String ASSET_PATH = "android/assets/textures";

    // TODO: 2016-12-01 copy assets file to "assets-raw" and check if working
    public static void main(String[] args) {
        final TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.atlasExtension = ".pack";
        settings.pot = false;
        TexturePacker.process(settings, RAW_ASSET_PATH, ASSET_PATH, "texture");
    }
}
