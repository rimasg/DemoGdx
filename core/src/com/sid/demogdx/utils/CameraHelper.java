package com.sid.demogdx.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.sid.demogdx.entities.GameObject;


/**
 * Camera will follow the Target
 */
public final class CameraHelper {
    private static Camera cam;
    private static GameObject target;
    private static Vector3 directionVec = new Vector3();

    private CameraHelper() { }

    public static void setCam(Camera cam) {
        CameraHelper.cam = cam;
    }

    public static void setTarget(GameObject target) {
        CameraHelper.target = target;
    }

    public static void update(float delta) {
        if ((null != cam) && (null != target)) {
            directionVec.set(target.pos.x, target.pos.y, 0);
            cam.position.lerp(directionVec, delta);
            cam.update();
        }
    }
}
