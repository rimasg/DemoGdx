package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2016.05.28 @ 14:02.
 */
public class SatelliteSpawner {
    private Vector2 pos = new Vector2();
    private AbstractCircle target;
    private Array<AbstractCircle> children = new Array<>();

    public SatelliteSpawner(float x, float y, AbstractCircle target) {
        pos.set(x, y);
        this.target = target;
    }

    public void update(float delta) {
        for (AbstractCircle child : children) {
            child.moveTo(target, delta);
        }
    }

    public void draw(ShapeRenderer renderer) {
        for (AbstractCircle child : children) {
            child.draw(renderer);
        }
    }

    public void spawnSatelite() {
        children.add(new SateliteCircle(pos.x, pos.y, AbstractCircle.RADIUS));
    }

    public void removeSatelite(AbstractCircle spawned) {
        children.removeValue(spawned, true);
    }

    public Array<AbstractCircle> getSatelites() {
        return children;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
