package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
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
    final Vector3 touchPos = new Vector3();

    Skin skin;

    private long secondsTime = 0L;
    private Timer.Task secondsTimer;

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
        startTimer();
    }

    private void startTimer() {
        secondsTimer = new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                secondsTime++;
            }
        }, 0, 1f);
    }

    public String getScreenTime() {
        int seconds = (int) (secondsTime % 60);
        int minutes = (int) ((secondsTime / 60) % 60);
        int hours = (int) ((secondsTime / 3600) % 24);
        String secondsStr = (seconds < 10 ? "0" : "") + seconds;
        String minutesStr = (minutes < 10 ? "0" : "") + minutes;
        String hoursStr = (hours < 10 ? "0" : "") + hours;
        return hoursStr + ":" + minutesStr + ":" + secondsStr;
    }

    private void resetVars() {
        secondsTime = 0;
    }

    @Override
    public void render(float delta) {
        // TODO: 2016.06.19 uncomment
//        Gdx.gl.glClearColor(0.75f, 0.75f, 0.75f, 1);
        Gdx.gl.glClearColor(0.f, 0.f, 0.f, 1);
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
        secondsTimer.cancel();
        resetVars();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
