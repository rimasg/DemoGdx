package com.sid.demogdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2016.10.04.
 */

public final class ProjectileMotion {
    public static int INITIAL_VELOCITY = 50;
    private static float G = 9.8f;
    private static float time = 1.0f;
    private static float stepping = 0.1f;
    private static int capacity = MathUtils.ceil(time / stepping);
    private static Array<Vector2> displacement = new Array<>(capacity);

    static {
        for (int i = 0; i < capacity; i++) {
            displacement.add(new Vector2());
        }
    }

    public static Array<Vector2> calcDisplacement(Vector2 start, Vector2 end, Vector2 impulse) {
        int i = 0;
        for (float t = stepping; t < time; t += stepping) {
            final float angleRad = end.angleRad(start);
            float x = start.x + INITIAL_VELOCITY * impulse.x * t * MathUtils.cos(angleRad);
            float y = start.y + INITIAL_VELOCITY * impulse.y * t * MathUtils.sin(angleRad) - 0.1f * G * t * t;
            displacement.get(i).set(x, y);
            i++;
        }
        return displacement;
    }

    private ProjectileMotion() { }
}
