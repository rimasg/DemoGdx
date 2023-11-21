package com.sid.demogdx.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

public class StackTowerScreen extends AbstractBox2dScreen {
    public StackTowerScreen(DemoGdx game) {
        super(game);
    }

    private final Array<Body> bodies = new Array<>();
    private final float defaultBoxWidth = 2.0f;
    private final float defaultBoxHeight = 0.5f;

    @Override
    public void render(float delta) {
        super.render(delta);

        cam.update();
        b2dr.render(world, cam.combined);

        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        renderBodyShapes();
        game.shapeRenderer.end();


        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        // TODO: 20/11/2023 Draw something
        game.batch.end();
    }

    private void renderBodyShapes() {
        game.shapeRenderer.setColor(Color.RED);
        for (Body body : bodies) {
            final Vector2 pos = body.getPosition();
            game.shapeRenderer.circle(pos.x, pos.y, 0.2f);
        }
    }

    @Override
    protected void loadAssets() {
        // TODO: 20/11/2023 Load assets
    }

    @Override
    protected void init() {
        super.init();

        Box2DUtils.createWorldBoundaries(world);
        createBlocks();
    }

    private void createBlocks() {
        for (int i = 0; i < 5; i++) {
            final Body body = Box2DUtils.createBox2dBoxBody(world,
                    Box2DConfig.WWV / 2.0f + MathUtils.random(0.1f * defaultBoxWidth, 0.4f * defaultBoxWidth),
                    Box2DConfig.WHV * 0.4f + i * defaultBoxHeight * 2.0f * 1.1f,
                    defaultBoxWidth, defaultBoxHeight);
            bodies.add(body);
        }
    }

    @Override
    public void hide() {
        super.hide();

        bodies.clear();
    }
}
