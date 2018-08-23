package com.sid.demogdx.screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;

/**
 * Created by Okis on 2016.03.20 @ 07:43.
 */
public abstract class AbstractBox2dScreen extends AbstractScreen {
    static final float TIME_STEP = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;

    World world;
    Box2DDebugRenderer b2dr;
    Array<Body> bodies = new Array<Body>();
    Array<Body> bodiesToRemove = new Array<Body>();
    Body hitBody = null;

    public AbstractBox2dScreen(DemoGdx game) {
        super(game);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        stepWorld(delta);
    }

    @Override
    protected void init() {
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);
        b2dr = new Box2DDebugRenderer();
    }

    private void stepWorld(float delta) {
        accumulator += Math.min(delta, 0.25f);

        while (accumulator >= TIME_STEP) {
            accumulator -= TIME_STEP;
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        b2dr.dispose();
        world.dispose();

        b2dr = null;
        world = null;
    }
}
