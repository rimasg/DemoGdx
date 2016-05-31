package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2016.05.28 @ 14:02.
 */
public class SatelliteSpawner {
    private Vector2 pos = new Vector2();
    private AbstractCircle target;
    private AbstractCircle spawned;
    private Array<AbstractCircle> children = new Array<>();
    private int initialNoOfSatellites = 3;
    private int distanceBetweenSatellites = 70;

    public SatelliteSpawner(float x, float y, AbstractCircle target) {
        pos.set(x, y);
        this.target = target;
        initSatellites();
    }

    private void initSatellites() {
        for (int i = initialNoOfSatellites - 1; i >= 0; i--) {
            children.add(new SateliteCircle(pos.x, pos.y + distanceBetweenSatellites * i, AbstractCircle.RADIUS, initialNoOfSatellites - i));
        }
    }

    public void update(float delta) {
        for (AbstractCircle child : children) {
            child.update(delta);
        }
    }

    public void draw(ShapeRenderer renderer) {
        for (AbstractCircle child : children) {
            child.draw(renderer);
        }
    }

    public void draw(SpriteBatch batch) {
        for (AbstractCircle child : children) {
            child.draw(batch);
        }
    }

    public void spawnSatellite() {
        if (!children.contains(spawned, true)) {
            spawned = children.first();
            spawned.setTarget(target);
            children.add(new SateliteCircle(pos.x, pos.y, AbstractCircle.RADIUS, children.peek().getScore() + 1));
            updateSatellitesTarget();
        }
    }

    private void updateSatellitesTarget() {
        for (int i = children.size - 1; i > 0; i--) {
            children.get(i).setTarget(children.get(i - 1));
        }
    }

    public void removeSatellite(AbstractCircle spawned) {
        children.removeValue(spawned, true);
        spawned.setArrivedToTarget(true);
    }

    public Array<AbstractCircle> getSatellites() {
        return children;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
