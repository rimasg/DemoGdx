package com.sid.demogdx.entities.circle;

/**
 * Created by Okis on 2016.05.27 @ 15:31.
 */
public class SateliteCircle extends AbstractCircle{
    public SateliteCircle(float x, float y, float radius, int score) {
        super(x, y, radius, score);
    }

    public SateliteCircle(AbstractCircle circle) {
        super(circle);
    }
}
