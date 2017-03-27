package com.sid.demogdx.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;


/**
 * The Camera will follow the Target on {@link #update(float)}
 */
public final class HunterCameraHelper {
    public static float VELOCITY = 4.f;

    private static Camera cam;
    private  static Body target;
    private static Vector3 targetVec = new Vector3();

    public static void setCam(Camera cam) {
        HunterCameraHelper.cam = cam;
    }

    public static void setTarget(Body target) {
        HunterCameraHelper.target = target;
    }

    public static void reset() {
        cam = null;
        target = null;
    }

    public static void update(float delta) {
        if ((null != cam) && (null != target)) {
            targetVec.set(cam.viewportWidth / 2, target.getPosition().y, 0);
            cam.position.lerp(targetVec, delta * VELOCITY);
            cam.update();
        }
    }

    private HunterCameraHelper() { }
}
