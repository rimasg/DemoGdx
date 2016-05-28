package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2016.05.27 @ 20:30.
 */
public class MasterCircleActor extends AbstractCircleActor {
    public float rotationAngle = 45f;

    Array<AbstractCircleActor> actors = new Array<>();
}
