package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.hunter.HunterSteerableObject;

/**
 * Created by Okis on 2017.03.26.
 */

public class PlayerComponent implements Component {
    public Body body;
    public HunterSteerableObject steerable;
}
