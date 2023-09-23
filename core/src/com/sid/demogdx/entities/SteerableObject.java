package com.sid.demogdx.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sid.demogdx.utils.SteeringUtils;

/**
 * Created by Okis on 2016.11.13.
 */

public class SteerableObject implements Steerable<Vector2> {
    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    private SteeringBehavior<Vector2> steeringBehavior;

    private Vector2 position = new Vector2();
    private Vector2 linearVelocity = new Vector2();
    private float angularVelocity;

    private boolean tagged;
    private float boundingRadius;
    private float maxLinearSpeed = 500.f;
    private float maxLinearAcceleration = 300.f;
    private float maxAngularSpeed = 5.f;
    private float maxAngularAcceleration = 100.f;

    private boolean independentFacing;

    private Sprite sprite;
    private float width, height;

    public SteerableObject(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new SteerableLocation();
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    public boolean isIndependentFacing() {
        return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setBounds(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void update(float delta) {
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(steeringOutput);
            applySteering(steeringOutput, delta);
            wrapAround(position, width, height);
        } else {
            position.mulAdd(linearVelocity, delta);
            wrapAround(position, width, height);
        }
        sprite.setPosition(position.x, position.y);
    }

    private void wrapAround(Vector2 pos, float maxX, float maxY) {
        if (pos.x > maxX) pos.x = 0.f;
        if (pos.x < 0) pos.x = maxX;
        if (pos.y > maxY) pos.y = 0.f;
        if (pos.y < 0) pos.y = maxY;
    }

    private void applySteering(SteeringAcceleration<Vector2> steering, float delta) {
        position.mulAdd(linearVelocity, delta);
        linearVelocity.mulAdd(steering.linear, delta).limit(getMaxLinearSpeed());

        if (independentFacing) {
            sprite.setRotation(sprite.getRotation() + (angularVelocity * delta) * MathUtils.radiansToDegrees);
            angularVelocity += steering.angular * delta;
        } else {
            if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
                final float newOrientation = vectorToAngle(linearVelocity);
                angularVelocity = (newOrientation - getOrientation() * MathUtils.degreesToRadians) * delta;
                sprite.setRotation(newOrientation * MathUtils.radiansToDegrees);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
