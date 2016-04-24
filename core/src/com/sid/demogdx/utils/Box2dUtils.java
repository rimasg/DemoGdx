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
    private Box2dUtils() { }

    public static Body createBox2dCircleBody(World world, float posX, float posY) {
        return createBox2dCircleBody(world, posX, posY, null);
    }

    public static Body createBox2dCircleBody(World world, float posX, float posY, FixtureDef fixtureDef) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(AppConfig.BALL_RADIUS);

        BodyDef characterBodyDef = new BodyDef();
        characterBodyDef.type = BodyDef.BodyType.DynamicBody;
        characterBodyDef.position.set(posX, posY);
        Body characterBody = world.createBody(characterBodyDef);

        FixtureDef ballFixtureDef;
        if (fixtureDef != null) {
            ballFixtureDef = fixtureDef;
            ballFixtureDef.shape = circleShape;
        } else {
            ballFixtureDef = new FixtureDef();
            ballFixtureDef.shape = circleShape;
            ballFixtureDef.friction = 0.4f;
            ballFixtureDef.restitution = 0.4f;
            ballFixtureDef.density = 1.0f;
        }
        characterBody.createFixture(ballFixtureDef);

        circleShape.dispose();

        return characterBody;
    }
}
