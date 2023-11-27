package com.sid.demogdx.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

public class StackTowerScreen extends AbstractBox2dScreen {
    public StackTowerScreen(DemoGdx game) {
        super(game);
    }

    private Body upperBox;
    private Body lowerBox;
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
        game.shapeRenderer.circle(upperBox.getPosition().x, upperBox.getPosition().y, 0.2f);
        game.shapeRenderer.circle(lowerBox.getPosition().x, lowerBox.getPosition().y, 0.2f);
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
        upperBox = Box2DUtils.createBox2dBoxBody(world,
                Box2DConfig.WWV / 2.0f + MathUtils.random(-0.4f * defaultBoxWidth, 0.4f * defaultBoxWidth),
                Box2DConfig.WHV * 0.2f + 1 * defaultBoxHeight * 2.0f * 1.1f,
                defaultBoxWidth, defaultBoxHeight);
        lowerBox = Box2DUtils.createBox2dBoxBody(world,
                Box2DConfig.WWV / 2.0f,
                Box2DConfig.WHV * 0.2f + 0 * defaultBoxHeight * 2.0f * 1.1f,
                defaultBoxWidth, defaultBoxHeight);
    }

}
