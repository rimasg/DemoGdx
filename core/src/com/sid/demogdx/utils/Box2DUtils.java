package com.sid.demogdx.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Okis on 2016.03.06 @ 20:28.
 */
public final class Box2DUtils {

    public static Body createBox2dCircleBody(World world, float posX, float posY) {
        return createBox2dBody(world, posX, posY, null, Shape.Type.Circle);
    }

    public static Body createBox2dPolygonBody(World world, float posX, float posY) {
        return createBox2dBody(world, posX, posY, null, Shape.Type.Polygon);
    }

    public static Body createBox2dBody(World world, float posX, float posY, FixtureDef fixtureDef, Shape.Type shapeType) {
        Shape shape = createShape(shapeType);
        FixtureDef localFixtureDef;
        if (fixtureDef != null) {
            localFixtureDef = fixtureDef;
        } else {
            localFixtureDef = createFixture();
            localFixtureDef.shape = shape;
        }

        Body body = world.createBody(createBodyDef(posX, posY));
        body.createFixture(localFixtureDef);
        shape.dispose();
        return body;
    }

    private static BodyDef createBodyDef(float posX, float posY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, posY);
        return bodyDef;
    }

    private static Shape createShape(Shape.Type shapeType) {
        Shape shape = null;
        switch (shapeType) {
            case Circle:
                shape = new CircleShape();
                shape.setRadius(Box2DConfig.BALL_RADIUS);
                break;
            case Polygon:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(0.3f, 0.3f);
                break;
            case Chain:
                shape = new ChainShape();
                break;
            case Edge:
                shape = new EdgeShape();
                break;
        }
        return shape;
    }

    private static FixtureDef createFixture() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.density = 1.0f;
        return fixtureDef;
    }

    public static Body createWorldBoundaries(World world) {
        BodyDef bodyDef = new BodyDef();
        Body body = world.createBody(bodyDef);

        EdgeShape shape = new EdgeShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 1.0f;
        fixtureDef.restitution = 0.f;

        shape.set(new Vector2(0.f, 0.f), new Vector2(Box2DConfig.WWV, 0.f)); /* ground */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(0.f, Box2DConfig.WHV), new Vector2(Box2DConfig.WWV, Box2DConfig.WHV)); /* top */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(0.0001f, 0.f), new Vector2(0.0001f, Box2DConfig.WHV)); /* left side */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(Box2DConfig.WWV, 0.f), new Vector2(Box2DConfig.WWV, Box2DConfig.WHV)); /* right side */
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    private Box2DUtils() { }
}
