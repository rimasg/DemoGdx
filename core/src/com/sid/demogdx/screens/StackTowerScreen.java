package com.sid.demogdx.screens;

import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

public class StackTowerScreen extends AbstractBox2dScreen {
    public StackTowerScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        cam.update();
        b2dr.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        // TODO: 20/11/2023 Draw something
        game.batch.end();
    }

    @Override
    protected void loadAssets() {
        // TODO: 20/11/2023 Load assets
    }

    @Override
    protected void init() {
        super.init();

        Box2DUtils.createWorldBoundaries(world);
        createBlock();
    }

    private void createBlock() {
        Box2DUtils.createBox2dPolygonBody(world, Box2DConfig.WWV / 2.0f, Box2DConfig.WHV * 0.8f);
    }
}
