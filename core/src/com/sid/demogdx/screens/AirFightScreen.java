package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.SteerableObject;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.CameraHelper;

/**
 * Created by Okis on 2016.10.09.
 */

public class AirFightScreen extends AbstractScreen {
    private ShapeRenderer shapeRenderer;
    private int worldW, worldH;
    private Touchpad touchpad;
    private SteerableObject airplane;
    private SteerableObject rocket;

    public AirFightScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();
        worldW = AppConfig.WWP;
        worldH = AppConfig.WHP;
        CameraHelper.setCam(stage.getCamera());
//        CameraHelper.setTarget(); TODO: 2016.11.13 set target

        airplane = new SteerableObject(new Sprite(skin.getRegion("airplane")));
        airplane.setPosition(new Vector2(stage.getWidth() / 2, stage.getHeight() / 2));
        airplane.setBounds(stage.getWidth(), stage.getHeight());
        rocket = new SteerableObject(new Sprite(skin.getRegion("rocket")));
        rocket.setPosition(new Vector2(stage.getWidth() / 2, 20.f));
        rocket.setBounds(stage.getWidth(), stage.getHeight());
        rocket.setMaxLinearSpeed(100.f);
        final Evade<Vector2> evadeBehaviour = new Evade<>(airplane, rocket);
//        airplane.setSteeringBehavior(evadeBehaviour); // TODO: 2016.11.13 uncomment later
        final Pursue<Vector2> pursue = new Pursue<>(rocket, airplane)
                .setMaxPredictionTime(0.1f);
        rocket.setSteeringBehavior(pursue);

        createHud();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        CameraHelper.update(delta);
        airplane.update(delta);
        rocket.update(delta);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        // TODO: 2016.11.13 add code
        airplane.draw(game.batch);
        rocket.draw(game.batch);
        game.batch.end();

        stage.act();
        stage.draw();
    }

    private void createHud() {
        final Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        touchpad = new Touchpad(20, skin);
        touchpad.setSize(20.f, 20.f);
        table.add(touchpad).expand().bottom();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        airplane.getLinearVelocity()
                .set(touchpad.getKnobPercentX() * 200.f, touchpad.getKnobPercentY() * 200.f)
                .limit(airplane.getMaxLinearSpeed());
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}