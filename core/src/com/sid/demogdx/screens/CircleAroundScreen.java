package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.circle.AbstractCircle;
import com.sid.demogdx.entities.circle.MasterCircle;
import com.sid.demogdx.entities.circle.SatelliteSpawner;
import com.sid.demogdx.utils.AppConfig;

/**
 * Created by Okis on 2016.05.08 @ 13:46.
 */
public class CircleAroundScreen extends AbstractScreen {
    private ShapeRenderer shapeRenderer;
    private MasterCircle master;
    private SatelliteSpawner satelliteSpawner;
    private Array<AbstractCircle> spawnedSatellites;

    public CircleAroundScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.RED);

        int worldW = AppConfig.WWP;
        int worldH = AppConfig.WHP;

        int masterPosX = worldW / 2;
        int masterPosY = worldH - masterPosX;
        master = new MasterCircle(masterPosX, masterPosY, AbstractCircle.RADIUS * 2, masterPosX * 0.8f);
        satelliteSpawner = new SatelliteSpawner(masterPosX, worldH * 0.1f, master);
        spawnedSatellites = satelliteSpawner.getSatellites();

        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // return super.touchDown(screenX, screenY, pointer, button);
                stage.getCamera().unproject(touchPos.set(screenX, screenY, 0));
                spawnNewSatellite();
                return false;
            }
        };

        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();
        master.update(delta);
        satelliteSpawner.update(delta);
        checkForCollision();

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        master.draw(shapeRenderer);
        satelliteSpawner.draw(shapeRenderer);
        shapeRenderer.end();

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        master.draw(game.batch);
        satelliteSpawner.draw(game.batch);
        game.batch.end();

    }

    private void spawnNewSatellite() {
        satelliteSpawner.spawnSatelite();
    }

    private void checkForCollision() {
        for (AbstractCircle spawnedSatellite : spawnedSatellites) {
            if ((master.getPos().dst2(spawnedSatellite.getPos()) < master.getOuterRadius() * master.getOuterRadius())) {
                if (master.isCollision(spawnedSatellite)) {
                    changeColorOnCollision();
                }
                master.addChild(spawnedSatellite);
                master.incrementScore();
                satelliteSpawner.removeSatellite(spawnedSatellite);
            }
        }
    }

    private void changeColorOnCollision() {
        shapeRenderer.setColor(MathUtils.random(0.2f, 0.8f), MathUtils.random(0.2f, 0.8f), MathUtils.random(0.2f, 0.8f), 1f);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
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
        shapeRenderer.dispose();
    }
}
