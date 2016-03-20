package com.sid.demogdx.utils;

/**
 * Created by Okis on 2015.07.08.
 */
public final class AppConfig {
    private AppConfig() {
    }

    public static final boolean DEBUG = false;
    public static final String DEBUG_TEXT = "Running Circle";
    // S4 mini resolution
    public static final int WWP = 540; /* WORLD_WIDTH_PIXEL */
    public static final int WHP = 960; /* WORLD_HEIGHT_PIXEL */
    //
    public static final int WWV = 9; /* WORLD_WIDTH_VIRTUAL */
    public static final int WHV = 16; /* WORLD_HEIGHT_VIRTUAL */
    //
    public static final float unitScale16 = 1 / 16f; /* Box2d world scale */
    public static final float unitScale32 = 1 / 32f; /* Box2d world scale */
}
