package com.sid.demogdx.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Okis on 2016.03.06 @ 20:28.
 */
public final class Box2dUtils {
    private static final String TAG = "Box2dUtils";
    private Box2dUtils() { }

    public static Body createBox2dCircleBody(World world, float posX, float posY) {
        return createBox2dBody(world, posX, posY, null, Shape.Type.Circle);
    }

    public static Body createBox2dPolygonBody(World world, float posX, float posY) {
        return createBox2dBody(world, posX, posY, null, Shape.Type.Polygon);
    }

    public static Body createBox2dBody(World world, float posX, float posY, FixtureDef fixtureDef, Shape.Type type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, posY);
        Body body = world.createBody(bodyDef);

        FixtureDef localFixtureDef;
        if (fixtureDef != null) {
            localFixtureDef = fixtureDef;
        } else {
            localFixtureDef = new FixtureDef();
            localFixtureDef.friction = 0.4f;
            localFixtureDef.restitution = 0.4f;
            localFixtureDef.density = 1.0f;
        }

        Shape shape;
        switch (type) {
            case Circle:
                shape = new CircleShape();
                shape.setRadius(AppConfig.BALL_RADIUS);
                localFixtureDef.shape = shape;
                break;
            case Polygon:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(0.8f, 0.4f);
                localFixtureDef.shape = shape;
                break;
            default:
                shape = new EdgeShape();
                break;
        }

        body.createFixture(localFixtureDef);
        shape.dispose();
        return body;
    }

    public static void createWorldBoundaries(World world) {
        EdgeShape shape = new EdgeShape();

        BodyDef bodyDef = new BodyDef();
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 1.0f;
        fixtureDef.restitution = 0.f;

        shape.set(new Vector2(0.f, 0.f), new Vector2(AppConfig.WWV, 0.f)); /* ground */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(0.0001f, 0.f), new Vector2(0.f, AppConfig.WHV)); /* left side */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(AppConfig.WWV, 0.f), new Vector2(AppConfig.WWV, AppConfig.WHV)); /* right side */
        body.createFixture(fixtureDef);

        shape.dispose();
    }
}
