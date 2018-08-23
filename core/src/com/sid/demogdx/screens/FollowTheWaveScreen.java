package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.wave.Runner;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.CameraHelper;

/**
 * Created by Okis on 2016.10.09.
 */

public class FollowTheWaveScreen extends AbstractScreen {
    private ShapeRenderer shapeRenderer;
    private int worldW, worldH;
    private Vector2[] dataSet = new Vector2[10];
    private Vector2[] dataSetOther = new Vector2[10];
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
        runner = new Runner(new Sprite(Assets.inst().getRegion(RegionNames.STAR)));
        CameraHelper.setCam(stage.getCamera());
        CameraHelper.setTarget(runner);
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

    @Override
    protected void loadAssets() {

    }

    @Override
    protected void init() {

    }

    private void initDataSet() {
        dataSet[0] = new Vector2(0.f, 50.f);
        dataSet[1] = new Vector2(40.f, 200.f);
        dataSet[2] = new Vector2(120.f, 500.f);
        dataSet[3] = new Vector2(300.f, 800.f);
        dataSet[4] = new Vector2(600.f, 1000.f);
        dataSet[5] = new Vector2(1000.f, 800.f);
        dataSet[6] = new Vector2(800.f, 600.f);
        dataSet[7] = new Vector2(600.f, 400.f);
        dataSet[8] = new Vector2(300.f, 200.f);
        dataSet[9] = new Vector2(200.f, 100.f);
        float offset = 100.0f;
        dataSetOther[0] = new Vector2(0.f + offset, 50.f);
        dataSetOther[1] = new Vector2(40.f + offset, 200.f);
        dataSetOther[2] = new Vector2(120.f + offset, 500.f);
        dataSetOther[3] = new Vector2(300.f + offset, 800.f);
        dataSetOther[4] = new Vector2(600.f + offset, 1000.f);
        dataSetOther[5] = new Vector2(1000.f + offset, 800.f);
        dataSetOther[6] = new Vector2(800.f + offset, 600.f);
        dataSetOther[7] = new Vector2(600.f + offset, 400.f);
        dataSetOther[8] = new Vector2(300.f + offset, 200.f);
        dataSetOther[9] = new Vector2(200.f + offset, 100.f);
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

    private void drawStar(float delta, Vector2[] points) {
        current += delta * speed;
        if (current >= 1.0f) {
            current -= 1.0f;
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
        runner.update(delta);
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
        runner.update(delta);
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
    public void hide() {
        super.hide();
        CameraHelper.reset();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
