package com.sid.demogdx.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Okis on 2016.03.06 @ 20:28.
 */
public final class Box2dUtils {
    private Box2dUtils() {
    }

    public static Body createBox2dCircleBody(World world, float posX, float posY) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        BodyDef characterBodyDef = new BodyDef();
        characterBodyDef.type = BodyDef.BodyType.DynamicBody;
        characterBodyDef.position.set(posX, posY);
        Body characterBody = world.createBody(characterBodyDef);

        FixtureDef charFixtureDef = new FixtureDef();
        charFixtureDef.shape = circleShape;
        charFixtureDef.restitution = 0.2f;
        charFixtureDef.density = 0.5f;
        characterBody.createFixture(charFixtureDef);

        circleShape.dispose();

        return characterBody;
    }
}
