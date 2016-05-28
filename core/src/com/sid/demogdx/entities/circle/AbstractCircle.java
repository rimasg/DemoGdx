package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Okis on 2016.05.27 @ 14:51.
 */
public abstract class AbstractCircle {
    protected static final String TAG = AbstractCircle.class.getSimpleName();
    public static final float VELOCITY = 500f;
    public static final float RADIUS = 10f;
    private Vector2 pos = new Vector2();
    private Vector2 vel = new Vector2();
    private Circle boundingCircle = new Circle();

    public AbstractCircle(float x, float y, float radius) {
        this.pos.set(x, y);
        this.boundingCircle.setPosition(pos);
        this.boundingCircle.radius = radius;
    }

    public boolean overlaps(Circle c) {
        return boundingCircle.overlaps(c);
    }

    private Vector2 rotationVec = new Vector2();

    /**
     *
     * @param targetCircle to rotate around
     * @param degrees angle in degrees
     */
    public void rotateAround(AbstractCircle targetCircle, float degrees) {
        rotationVec.set(pos.x, pos.y);
        rotationVec.sub(targetCircle.pos.x, targetCircle.pos.y);
        rotationVec.rotate(degrees);
        pos.set(targetCircle.pos.x + rotationVec.x, targetCircle.pos.y + rotationVec.y);
        updateBoundingCircle();
    }

    public void moveTo(AbstractCircle targetCircle, float delta) {
        vel.set(targetCircle.getPos());
        vel.sub(pos).nor().scl(VELOCITY);
        pos.mulAdd(vel, delta);
        updateBoundingCircle();
    }

    public void update(float delta){

    }

    public void draw(ShapeRenderer renderer) {
        renderer.circle(boundingCircle.x, boundingCircle.y, boundingCircle.radius);
    }

    public void setX(float x) {
        pos.x = x;
        updateBoundingCircle();
    }

    public void setY(float y) {
        pos.y = y;
        updateBoundingCircle();
    }

    public void setPos(float x, float y) {
        pos.set(x, y);
        updateBoundingCircle();
    }

    public Vector2 getPos() {
        return pos;
    }

    private void updateBoundingCircle() {
        boundingCircle.setPosition(pos);
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public void setBoundingCircle(Circle boundingCircle) {
        this.boundingCircle = boundingCircle;
    }
}
