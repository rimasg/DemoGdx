package com.sid.demogdx.hunter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.entities.SteerableBox2DObject;

/**
 * Created by Okis on 2017.04.07.
 */

public class HunterSteerableObject extends SteerableBox2DObject {

    private SeekAndAvoidSB seekAndAvoidSB;

    public HunterSteerableObject(TextureRegion region, Body body, float boundingRadius) {
        super(region, body, boundingRadius);
    }

    public SeekAndAvoidSB getSeekAndAvoidSB() {
        return seekAndAvoidSB;
    }

    public void setSeekAndAvoidSB(SeekAndAvoidSB seekAndAvoidSB) {
        this.seekAndAvoidSB = seekAndAvoidSB;
    }
}