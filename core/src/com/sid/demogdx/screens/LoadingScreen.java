package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.utils.Box2DConfig;

/**
 * Created by Okis on 2017.04.02.
 */

public class LoadingScreen extends ScreenAdapter {
    private DemoGdx game;
    private final OrthographicCamera cam;
    private final Viewport viewport;

    private float lineWidth = Box2DConfig.WWV;
    private float lineHeight = Box2DConfig.WHV * 0.1f;
    private float lineYpos = Box2DConfig.WHV / 2.0f;
    private float waitTime = 0.5f;

    public LoadingScreen(DemoGdx game) {
        this.game = game;
        this.cam = new OrthographicCamera();
        this.viewport = new FitViewport(Box2DConfig.WWV, Box2DConfig.WHV, cam);
        this.viewport.apply(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.rect(0, lineYpos, lineWidth, lineHeight);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.rect(0, lineYpos, lineWidth * Assets.inst().getProgress(), lineHeight);
        game.shapeRenderer.end();
        if (Assets.inst().update()) {
            if ((waitTime -= delta) <= 0) {
                game.setScreen(game.getMainMenuScreen());
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }
}
