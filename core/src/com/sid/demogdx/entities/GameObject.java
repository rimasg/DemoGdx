package com.sid.demogdx.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Okis on 2016.10.12.
 */

public abstract class GameObject {
    public final Vector2 pos = new Vector2();
    protected Sprite sprite;

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(float x, float y) {
        this.pos.set(x, y);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(pos.x - (sprite.getWidth() / 2), pos.y - (sprite.getWidth() / 2));
        sprite.draw(batch);
    }
}
