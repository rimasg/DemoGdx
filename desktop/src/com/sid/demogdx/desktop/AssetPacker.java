package com.sid.demogdx.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by SID on 2016-12-01.
 */

public class AssetPacker {
    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSET_PATH = "android/assets";

    public static void main(String[] args) {
        final String pathSeparator = "/";

        final String gamePlayPath = "gameplay";
        TexturePacker.process(
                RAW_ASSET_PATH + pathSeparator + gamePlayPath,
                ASSET_PATH + pathSeparator + gamePlayPath,
                "gameplay");

        final String uiPath = "ui";
        TexturePacker.process(
                RAW_ASSET_PATH + pathSeparator + uiPath,
                ASSET_PATH + pathSeparator + uiPath,
                "skin");
    }
}
