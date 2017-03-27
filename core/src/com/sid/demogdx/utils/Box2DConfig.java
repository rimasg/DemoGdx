package com.sid.demogdx.utils;

/**
 * Created by Okis on 2015.07.08.
 */
public final class Box2DConfig {
    public static final boolean DEBUG = false;
    public static final String DEBUG_TEXT = "Running Circle";
    // Samsung S4 Mini resolution
    public static final int WWP = 540; /* WORLD_WIDTH_PIXEL */
    public static final int WHP = 960; /* WORLD_HEIGHT_PIXEL */
    //
    public static final int WWV = 9; /* WORLD_WIDTH_VIRTUAL */
    public static final int WHV = 16; /* WORLD_HEIGHT_VIRTUAL */
    //
    public static final float unitScale16 = 1 / 16f; /* Box2d world scale */
    public static final float unitScale32 = 1 / 32f; /* Box2d world scale */
    //
    public static final float BALL_RADIUS = 0.5f;

    public static float pixelsToMeters (Float pixels, float scale) {
        return (float) pixels * scale;
    }

    public static int metersToPixels(float meters, float scale) {
        return (int) (meters / scale);
    }

    private Box2DConfig() { }
}
