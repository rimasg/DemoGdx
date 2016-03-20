package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sid.demogdx.Assets;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;

/**
 * Created by SID on 2016-03-03 @ 17:13 @ 17:19.
 */
public abstract class AbstractScreen implements Screen {
    String TAG;

    final DemoGdx game;
    final OrthographicCamera cam;
    final Viewport viewport;
    final Stage stage;
    final Assets assets = Assets.inst();

    Skin skin;

    public AbstractScreen(final DemoGdx game) {
        this.TAG = getClass().getSimpleName();
        this.game = game;
        this.cam = new OrthographicCamera();
        this.viewport = new FitViewport(AppConfig.WWV, AppConfig.WHV, cam);
        this.viewport.apply(true);

        this.stage = new Stage(new FitViewport(AppConfig.WWP, AppConfig.WHP), game.batch);
        Gdx.input.setInputProcessor(stage);

        loadAssets();
    }

    private void loadAssets() {
        skin = assets.get("skin.json", Skin.class);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {

    }
}
