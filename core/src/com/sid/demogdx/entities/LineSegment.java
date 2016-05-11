package com.sid.demogdx.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Okis on 2016.05.08 @ 14:02.
 */
public class LineSegment {
    private TextureAtlas.AtlasRegion lineSegmentRegion;
    private float x, y, angle;

    public LineSegment(TextureAtlas.AtlasRegion lineSegmentRegion) {
        this.lineSegmentRegion = lineSegmentRegion;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getWidth() {
        return lineSegmentRegion.getRegionWidth();
    }

    public int getHeight() {
        return lineSegmentRegion.getRegionHeight();
    }

    public void draw(Batch batch) {
        batch.draw(lineSegmentRegion, x, y,
                x,
                y - lineSegmentRegion.getRegionHeight() / 2,
                lineSegmentRegion.getRegionWidth(),
                lineSegmentRegion.getRegionHeight(), 1f, 1f, angle);
    }
}
