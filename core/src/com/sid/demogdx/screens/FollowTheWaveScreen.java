package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.wave.Runner;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.CameraHelper;

/**
 * Created by Okis on 2016.10.09.
 */

public class FollowTheWaveScreen extends AbstractScreen {
    private ShapeRenderer shapeRenderer;
    private int worldW, worldH;
    private Vector2[] dataSet = new Vector2[5];
    private Vector2[] dataSetOther = new Vector2[5];
    private CatmullRomSpline<Vector2> spline;
    private CatmullRomSpline<Vector2> splineOther;
    private int k = 100;
    private Vector2[] points = new Vector2[k];
    private Vector2[] pointsOther = new Vector2[k];
    private float speed = 0.15f;
    private float current = 0.0f;
    private Runner runner;
    private boolean togglePath;

    public FollowTheWaveScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();
        worldW = AppConfig.WWP;
        worldH = AppConfig.WHP;
        initDataSet();
        initSpline();
        cachePathPoints();
        runner = new Runner(new Sprite(skin.getRegion("star")));
        CameraHelper.setCam(stage.getCamera());
        CameraHelper.setTarget(runner);
    }

    private void initDataSet() {
        dataSet[0] = new Vector2(50.0f, worldH * 0.2f);
        dataSet[1] = new Vector2(worldW / 4, worldH / 2);
        dataSet[2] = new Vector2(50.0f, worldH * 0.8f);
        dataSet[3] = new Vector2(worldW / 2, worldH *  0.8f);
        dataSet[4] = new Vector2(worldW * 0.9f, worldH * 0.3f);
        float offset = 50.0f;
        dataSetOther[0] = new Vector2(50.0f + offset, worldH * 0.2f + offset);
        dataSetOther[1] = new Vector2(worldW / 4 + offset, worldH / 2);
        dataSetOther[2] = new Vector2(50.0f + offset, worldH * 0.8f - offset);
        dataSetOther[3] = new Vector2(worldW / 2 - offset, worldH *  0.8f);
        dataSetOther[4] = new Vector2(worldW * 0.9f - offset, worldH * 0.3f + offset);
/*
        dataSetOther[0] = new Vector2(worldW * 0.1f, worldH * 0.3f);
        dataSetOther[1] = new Vector2(worldW / 3.0f, worldH * 0.5f);
        dataSetOther[2] = new Vector2(worldW * 0.1f, worldH * 0.7f);
        dataSetOther[3] = new Vector2(worldW / 3.0f, worldH * 0.6f);
        dataSetOther[4] = new Vector2(worldW * 0.8f, worldH * 0.4f);
*/
    }

    private void initSpline() {
        spline = new CatmullRomSpline<>(dataSet, true);
        splineOther = new CatmullRomSpline<>(dataSetOther, true);
    }

    private void cachePathPoints() {
        for (int i = 0; i < k; ++i) {
            points[i] = new Vector2();
            pointsOther[i] = new Vector2();
            spline.valueAt(points[i], ((float) i) / ((float) k - 1));
            splineOther.valueAt(pointsOther[i], ((float) i) / ((float) k - 1));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin();
        drawPath();
        shapeRenderer.end();

        CameraHelper.update(delta);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        if (togglePath) {
            drawStar(delta, points);
        } else {
            drawStar(delta, pointsOther);
        }
        game.batch.end();
    }

    private void drawStar(float delta, Vector2[] points) {
        current += delta * speed;
        if (current >= 1) {
            current -= 1;
        }
        float place = current * k;
        Vector2 first = points[(int) place];
        Vector2 second;
        if (((int) place + 1) < k) {
            second = points[(int) place + 1];
        } else {
            second = points[0];
        }
        float t = place - ((int) place);

        runner.setPos(first.x + (second.x - first.x) * t, first.y + (second.y - first.y) * t);
        runner.draw(game.batch);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            togglePath = !togglePath;
        }
    }

    private void drawPath() {
        for (int i = 0; i < k - 1; i++) {
            shapeRenderer.line(points[i], points[i + 1]);
            shapeRenderer.line(pointsOther[i], pointsOther[i + 1]);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}
