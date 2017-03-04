package com.sid.demogdx.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.sid.demogdx.entities.GameObject;


/**
 * The Camera will follow the Target on {@link #update(float)}
 */
public final class CameraHelper {
    public static float VELOCITY = 4.f;

    private static Camera cam;
    private static GameObject target;
    private static Vector3 targetVec = new Vector3();

    private CameraHelper() { }

    public static void setCam(Camera cam) {
        CameraHelper.cam = cam;
    }

    public static void reset() {
        cam = null;
        target = null;
    }

    public static void setTarget(GameObject target) {
        CameraHelper.target = target;
    }

    public static void update(float delta) {
        if ((null != cam) && (null != target)) {
            targetVec.set(target.pos.x, target.pos.y, 0);
            cam.position.lerp(targetVec, delta * VELOCITY);
            cam.update();
        }
    }
}
