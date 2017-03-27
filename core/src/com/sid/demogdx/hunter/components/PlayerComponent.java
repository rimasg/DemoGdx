package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.entities.SteerableBox2DObject;

/**
 * Created by Okis on 2017.03.26.
 */

public class PlayerComponent implements Component {
    public Body body = null;
    public SteerableBox2DObject steerable = null;
}
