package com.sid.demogdx.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;

/**
 * Created by SID on 2016.03.01 @ 20:40 @ 17:19.
 */
public class Hero extends Actor {

    TextureRegion img;

    public Hero() {
        img = Assets.getRegion(RegionNames.HERO);
        setSize(100f, 100f);
        setBounds(getX(), getY(), getWidth(), getHeight());
        setOrigin(Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(img, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }
}
