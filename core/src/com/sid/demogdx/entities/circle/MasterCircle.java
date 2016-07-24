package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2016.05.27 @ 15:33.
 */
public class MasterCircle extends AbstractCircle {
    private static float rotationAnglePerSec = 75f;
    private float outerRadius;

    private Array<AbstractCircle> children = new Array<>();

    public MasterCircle(float x, float y, float radius, float outerRadius) {
        super(x, y, radius, 0);
        this.outerRadius = outerRadius;
    }

    public void addChildSatellite(AbstractCircle circle) {
        children.add(circle);
    }

    public boolean isCollision(AbstractCircle circle) {
        for (AbstractCircle child : children) {
            if (child.overlaps(circle)) {
                return true;
            }
        }
        return false;
    }

    public void changeColorOnCollision(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(MathUtils.random(0.2f, 0.8f), MathUtils.random(0.2f, 0.8f), MathUtils.random(0.2f, 0.8f), 1f);
    }

    public boolean hasInInnerCircle(AbstractCircle satelliteCircle) {
        return getPos().dst2(satelliteCircle.getPos()) < outerRadius * outerRadius;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateChildren(delta);
    }

    private void updateChildren(float delta) {
        for (AbstractCircle child : children) {
            child.rotateAround(this, rotationAnglePerSec * delta);
            child.update(delta);
        }
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        super.draw(renderer);
        for (AbstractCircle child : children) {
            child.draw(renderer);
            renderer.line(getPos(), child.getPos());
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (AbstractCircle child : children) {
            child.draw(batch);
        }
    }
}
