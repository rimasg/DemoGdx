package com.sid.demogdx.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.sid.demogdx.entities.GameObject;


/**
 * The Camera will follow the Target on {@link #update(float)}
 */
public final class CameraHelper {
    private static Camera mCam;
    private static GameObject mTarget;
    private static Vector3 mDirectionVec = new Vector3();

    private CameraHelper() { }

    public static void setCam(Camera cam) {
        mCam = cam;
    }

    public static void setTarget(GameObject target) {
        mTarget = target;
    }

    public static void update(float delta) {
        if ((null != mCam) && (null != mTarget)) {
            mDirectionVec.set(mTarget.pos.x, mTarget.pos.y, 0);
            mCam.position.lerp(mDirectionVec, delta);
            mCam.update();
        }
    }
}
