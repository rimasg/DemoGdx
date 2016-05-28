package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2016.05.27 @ 15:33.
 */
public class MasterCircle extends AbstractCircle {
    private float rotationAnglePerSec = 60f;
    private float outerRadius;

    private Array<AbstractCircle> children = new Array<>();

    public MasterCircle(float x, float y, float radius, float outerRadius) {
        super(x, y, radius);
        this.outerRadius = outerRadius;
    }

    public void addChild(AbstractCircle circle) {
        children.add(circle);
    }

    public boolean isCollision(AbstractCircle circle) {
        for (AbstractCircle child : children) {
            if (child.overlaps(circle.getBoundingCircle())) {
                return true;
            }
        }
        return false;
    }

    private void rotateChildren(float delta) {
        for (AbstractCircle child : children) {
            child.rotateAround(this, rotationAnglePerSec * delta);
            child.update(delta);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        rotateChildren(delta);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        super.draw(renderer);
        for (AbstractCircle child : children) {
            child.draw(renderer);
        }
    }

    public float getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(float outerRadius) {
        this.outerRadius = outerRadius;
    }

    public float getRotationAnglePerSec() {
        return rotationAnglePerSec;
    }

    public void setRotationAnglePerSec(float rotationAnglePerSec) {
        this.rotationAnglePerSec = rotationAnglePerSec;
    }
}
