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
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.CameraHelper;

/**
 * Created by Okis on 2016.10.09.
 */

public class FollowTheWaveScreen extends AbstractScreen {
    private ShapeRenderer shapeRenderer;
    private int worldW, worldH;
    private Vector2[] dataSet = new Vector2[7];
    private Vector2[] dataSetOther = new Vector2[7];
    private CatmullRomSpline<Vector2> spline;
    private CatmullRomSpline<Vector2> splineOther;
    private int k = 100;
    private Vector2[] points = new Vector2[k];
    private Vector2[] pointsOther = new Vector2[k];
    private float speed = 0.15f;
    private float current = 0.0f;
    private Runner runner;
    private boolean togglePath;
    private boolean isStarTranslationFinished = true;
    private float translationPos = 0.0f;

    public FollowTheWaveScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();
        worldW = Box2DConfig.WWP;
        worldH = Box2DConfig.WHP;
        initDataSet();
        initSpline();
        cachePathPoints();
        runner = new Runner(new Sprite(skin.getRegion("star")));
        CameraHelper.setCam(stage.getCamera());
        CameraHelper.setTarget(runner);
    }

    private void initDataSet() {
/*
        dataSet[0] = new Vector2(50.0f, worldH * 0.2f);
        dataSet[1] = new Vector2(worldW / 4, worldH / 2);
        dataSet[2] = new Vector2(50.0f, worldH * 0.8f);
        dataSet[3] = new Vector2(worldW / 2, worldH *  0.8f);
        dataSet[4] = new Vector2(worldW * 0.9f, worldH * 0.3f);
*/
        dataSet[0] = new Vector2(50.0f, 50.0f);
        dataSet[1] = new Vector2(worldW / 4, 200.0f);
        dataSet[2] = new Vector2(worldW / 2, 500.0f);
        dataSet[3] = new Vector2(worldW / 4, 800.0f);
        dataSet[4] = new Vector2(worldW * 0.8f, 1000.0f);
        dataSet[5] = new Vector2(worldW * 0.4f , 1500.0f);
        dataSet[6] = new Vector2(worldW * 0.6f, 2000.0f);
        float offset = 50.0f;
/*
        dataSetOther[0] = new Vector2(50.0f + offset, worldH * 0.2f + offset);
        dataSetOther[1] = new Vector2(worldW / 4 + offset, worldH / 2);
        dataSetOther[2] = new Vector2(50.0f + offset, worldH * 0.8f - offset);
        dataSetOther[3] = new Vector2(worldW / 2 - offset, worldH *  0.8f);
        dataSetOther[4] = new Vector2(worldW * 0.9f - offset, worldH * 0.3f + offset);
*/
        dataSetOther[0] = new Vector2(50.0f + offset, 50.0f);
        dataSetOther[1] = new Vector2(worldW / 4 + offset, 200.0f);
        dataSetOther[2] = new Vector2(worldW / 2 + offset, 500.0f);
        dataSetOther[3] = new Vector2(worldW / 4 + offset, 800.0f);
        dataSetOther[4] = new Vector2(worldW * 0.8f + offset, 1000.0f);
        dataSetOther[5] = new Vector2(worldW * 0.4f + offset, 1500.0f);
        dataSetOther[6] = new Vector2(worldW * 0.6f + offset, 2000.0f);
    }

    private void initSpline() {
        spline = new CatmullRomSpline<>(dataSet, false);
        splineOther = new CatmullRomSpline<>(dataSetOther, false);
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
            if (isStarTranslationFinished) {
                drawStar(delta, points);
            } else {
                float place = current * k;
                translateStar(delta, pointsOther[(int) place], points[(int) place]);
            }
        } else {
            if (isStarTranslationFinished) {
                drawStar(delta, pointsOther);
            } else {
                float place = current * k;
                translateStar(delta, points[(int) place], pointsOther[(int) place]);
            }
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

    private void translateStar(float delta, Vector2 from, Vector2 to) {
        translationPos += delta * 4.0f;
        if (translationPos >= 1.0f) {
            translationPos = 0.0f;
            isStarTranslationFinished = true;
            return;
        }
        runner.setPos(from.x + (to.x - from.x) * translationPos, from.y + (to.y - from.y) * translationPos);
        runner.draw(game.batch);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            isStarTranslationFinished = false;
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
