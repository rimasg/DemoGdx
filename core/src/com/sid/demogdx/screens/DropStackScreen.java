package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.Box2dUtils;

/**
 * Created by Okis on 2016.06.18 @ 19:32.
 */
public class DropStackScreen extends AbstractBox2dScreen {
    Body activeBody;

    public DropStackScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Box2dUtils.createWorldBoundaries(world);
        spawnBlock();
    }

    private void spawnBlock() {
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.8f, 0.4f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.density = 5.0f;
        activeBody = Box2dUtils.createBox2dBody(world,
                MathUtils.random(cam.viewportWidth * 0.2f, cam.viewportWidth * 0.8f),
                AppConfig.WHV * 0.8f, fixtureDef, Shape.Type.Polygon);
        polygonShape.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        cam.update();
        b2dr.render(world, cam.combined);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        if (Gdx.input.justTouched()) {
            dropBlock();
        }
    }

    private void dropBlock() {
        activeBody.getFixtureList().get(0).setDensity(1.0f);
        if (!bodies.contains(activeBody, true)) {
            bodies.add(activeBody);
        }
        spawnBlock();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
