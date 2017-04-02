package com.sid.demogdx.assets;

/**
 * Created by SID on 2016-12-01.
 */

public final class AssetPaths {
    static final String SKIN_JSON = "ui/skin.json";
    static final String TEXTURE_ATLAS = "gameplay/gameplay.atlas";

    private static final String PATH_FONT = "ui/fonts/";
    static final String FONT_OPEN_SANS_REGULAR = PATH_FONT + "OpenSans-Regular.ttf";
    static final String FONT_BLACK = PATH_FONT + "verdana_black_24.fnt";
    static final String FONT_WHITE = PATH_FONT + "verdana_white_24.fnt";

    static final String I18N = "i18n/strings";

    private static final String PATH_SOUND = "sounds/";
    static final String SOUND_COLLISION = PATH_SOUND + "click.ogg";

    private static final String PATH_MUSIC = "music/";
    static final String MUSIC_BG = PATH_MUSIC + "setuniman_guitar_loop.wav";

    private static final String PATH_PARTICLE = "particles/";
    static final String PE_EXPLOSION = PATH_PARTICLE + "explosion.p";
    static final String PE_GRAVITY_EXPLOSION = PATH_PARTICLE + "gravity_explosion.p";
    static final String PE_STAR_TRAIL = PATH_PARTICLE + "trail.p";
    static final String PE_SIMPLE_TRAIL = PATH_PARTICLE + "trail_simple.p";

    private static final String PATH_MAP = "maps/";
    static final String MAP_HUNTER = PATH_MAP + "hunter.tmx";

    private AssetPaths() { }
}
