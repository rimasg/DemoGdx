package com.sid.demogdx.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.utils.SteeringUtils;

/**
 * Created by Okis on 2016.11.13.
 */

public class SteerableBox2DObject implements Steerable<Vector2> {
    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
    private SteeringBehavior<Vector2> steeringBehavior;

    private TextureRegion region;
    private Body body;

    private Vector2 position = new Vector2();
    private Vector2 linearVelocity = new Vector2();
    private float angularVelocity;

    private float boundingRadius;
    private float maxLinearSpeed = 6.f;
//    private float maxLinearSpeed = 200.f;
    private float maxLinearAcceleration = 10.f;
//    private float maxLinearAcceleration = 300.f;
    private float maxAngularSpeed = 3.f;
//    private float maxAngularSpeed = 5.f;
    private float maxAngularAcceleration = 60.f;
//    private float maxAngularAcceleration = 100.f;

    private boolean tagged = false;
    private boolean independentFacing = false;

    public SteerableBox2DObject(TextureRegion region, Body body, float boundingRadius) {
        this.region = region;
        this.body = body;
        this.boundingRadius = boundingRadius;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
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
        return body.getPosition();
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(getPosition(), orientation);
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

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() {
        return steeringBehavior;
    }

    public boolean isIndependentFacing() {
        return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    public void update(float delta) {
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(steeringOutput);
            applySteering(steeringOutput, delta);
        }

//        wrapAround();
    }

    private void wrapAround(float maxX, float maxY) {
        float k = Float.POSITIVE_INFINITY;
        final Vector2 pos = body.getPosition();

        if (pos.x > maxX) k = pos.x = 0.f;
        if (pos.x < 0) k = pos.x = maxX;
        if (pos.y > maxY) k = pos.y = 0.f;
        if (pos.y < 0) k = pos.y = maxY;

        if (k != Float.POSITIVE_INFINITY) body.setTransform(pos, body.getAngle());
    }

    private void applySteering(SteeringAcceleration<Vector2> steering, float delta) {
        boolean anyAcceleration = false;

        if (!steeringOutput.linear.isZero()) {
            body.applyForceToCenter(steeringOutput.linear, true);
            anyAcceleration = true;
        }

        if (isIndependentFacing()) {
            if (steeringOutput.angular != 0) {
                body.applyTorque(steeringOutput.angular, true);
                anyAcceleration = true;
            }
        } else {
            final Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                final float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * delta);
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if (anyAcceleration) {
            final Vector2 velocity = body.getLinearVelocity();
            final float currentSpeedSquare = velocity.len2();
            final float maxLinearVelocity = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearVelocity * maxLinearVelocity) {
                body.setLinearVelocity(velocity.scl(maxLinearVelocity / (float) Math.sqrt(currentSpeedSquare)));
            }

            final float maxAngularVelocity = getMaxAngularSpeed();
            if (body.getAngularVelocity() > maxAngularVelocity) {
                body.setAngularVelocity(maxAngularVelocity);
            }
        }
    }
}
