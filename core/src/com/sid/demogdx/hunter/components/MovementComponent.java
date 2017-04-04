package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by SID on 2017-04-04.
 */

public class MovementComponent implements Component {
    public final Vector2 vel = new Vector2();
    public final Vector2 accel = new Vector2();
}
