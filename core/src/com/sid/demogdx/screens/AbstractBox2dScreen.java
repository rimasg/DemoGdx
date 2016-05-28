package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
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
    World world;
    Box2DDebugRenderer b2dr;
    Array<Body> bodies = new Array<Body>();
    Array<Body> bodiesToRemove = new Array<Body>();
    Body hitBody = null;

    public AbstractBox2dScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);
        b2dr = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        world.step(delta, 6, 2);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        b2dr.dispose();
        world.dispose();

        b2dr = null;
        world = null;
        Gdx.app.log(TAG, "dispose: Called");
    }
}
