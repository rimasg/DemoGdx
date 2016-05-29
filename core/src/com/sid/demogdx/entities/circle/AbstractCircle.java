package com.sid.demogdx.entities.circle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Okis on 2016.05.27 @ 14:51.
 */
public abstract class AbstractCircle {
    protected static final String TAG = AbstractCircle.class.getSimpleName();
    public static final float VELOCITY = 600f;
    public static final float RADIUS = 15f;
    private Vector2 pos = new Vector2();
    private Vector2 vel = new Vector2();
    private Vector2 targetPos = new Vector2();
    private AbstractCircle targetCircle;
    private boolean arrivedToTarget = true;
    private BitmapFont font;
    private int score;
    protected Circle boundingCircle = new Circle();

    public AbstractCircle(float x, float y, float radius, int score) {
        this.pos.set(x, y);
        this.boundingCircle.setPosition(pos);
        this.boundingCircle.radius = radius;
        this.font = new BitmapFont();
        this.score = score;
    }

    public AbstractCircle(AbstractCircle circle) {
        this(circle.pos.x, circle.pos.y, circle.boundingCircle.radius, circle.score);
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

    public void moveTo(Vector2 targetPos, float delta) {
        if (arrivedToTarget) return;
        if (!MathUtils.isZero(pos.dst2(targetPos), 25f)) {
            Gdx.app.log(TAG, "moveTo: dst2: " + pos.dst2(targetPos));

            vel.set(targetPos);
            vel.sub(pos).nor().scl(VELOCITY);
            pos.mulAdd(vel, delta);
            updateBoundingCircle();
        } else {
            arrivedToTarget = true;
        }
    }

    public void moveTo(float delta) {
        if (!arrivedToTarget) {
            moveTo(targetPos, delta);
        }
    }

    public void update(float delta){

    }

    public void draw(ShapeRenderer renderer) {
        drawCircle(renderer);
    }

    private void drawCircle(ShapeRenderer renderer) {
        renderer.circle(boundingCircle.x, boundingCircle.y, boundingCircle.radius);
    }

    public void draw (SpriteBatch batch) {
        drawScore(batch);
    }

    private GlyphLayout drawScore(SpriteBatch batch) {
        final float width = boundingCircle.radius * 2;
        final float height = font.getCapHeight();
        return font.draw(batch, String.valueOf(score),
                pos.x - width / 2,
                pos.y,
                width,
                Align.center, false);
    }

    public void setX(float x) {
        pos.x = x;
        updateBoundingCircle();
    }

    public float getX() {
        return pos.x;
    }

    public void setY(float y) {
        pos.y = y;
        updateBoundingCircle();
    }

    public float getY() {
        return pos.y;
    }

    public void setPos(float x, float y) {
        pos.set(x, y);
        updateBoundingCircle();
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int incrementScore() {
        return ++score;
    }

    public void setTargetPos(Vector2 targetPos) {
        this.targetPos.set(targetPos);
        arrivedToTarget = false;
    }

    public void setTargetPos(AbstractCircle targetCircle) {
        setTargetPos(targetCircle.pos);
    }

    private void updateBoundingCircle() {
        boundingCircle.setPosition(pos);
    }
}
