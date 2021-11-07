package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableObject;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.CameraHelper;

/**
 * Created by Okis on 2016.10.09.
 */

public class AirFightScreen extends AbstractScreen {
    private final float defaultLinearVelocity = 400.0f;

    private ShapeRenderer shapeRenderer;
    private int worldW, worldH;
    private Touchpad touchpad;
    private SteerableObject airplane;
    private SteerableObject rocket;

    public AirFightScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

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

    @Override
    protected void loadAssets() {
        shapeRenderer = new ShapeRenderer();
        worldW = Box2DConfig.WWP;
        worldH = Box2DConfig.WHP;
        CameraHelper.setCam(stage.getCamera());
        // CameraHelper.setTarget(); TODO: 2016.11.13 set target

        final Sprite airplaneSprite = new Sprite(Assets.inst().getRegion(RegionNames.AIRPLANE));
        airplaneSprite.setSize(32.f, 32.f);
        airplane = new SteerableObject(airplaneSprite);
        airplane.setPosition(new Vector2(stage.getWidth() / 2, stage.getHeight() / 2));
        airplane.setBounds(stage.getWidth(), stage.getHeight());
        // final Evade<Vector2> evadeBehaviour = new Evade<>(airplane, rocket);
        // airplane.setSteeringBehavior(evadeBehaviour);
        final LookWhereYouAreGoing<Vector2> lookWhereYouAreGoingBehaviour = new LookWhereYouAreGoing<>(airplane);
        airplane.setSteeringBehavior(lookWhereYouAreGoingBehaviour);

        final Sprite rocketSprite = new Sprite(Assets.inst().getRegion(RegionNames.ROCKET));
        rocketSprite.setSize(32.f, 64.f);
        rocket = new SteerableObject(rocketSprite);
        rocket.setPosition(new Vector2(stage.getWidth() / 2, 20.f));
        rocket.setBounds(stage.getWidth(), stage.getHeight());
        rocket.setMaxLinearSpeed(200.f);
        final Pursue<Vector2> pursue = new Pursue<>(rocket, airplane)
                .setMaxPredictionTime(0.1f);
        rocket.setSteeringBehavior(pursue);
    }

    @Override
    protected void init() {
        createHud();
        setInitialAirplaneSpeedAndDirection();
    }

    private void createHud() {
        final Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        touchpad = new Touchpad(20, skin);
        touchpad.setSize(20.f, 20.f);
        table.add(touchpad).expand().bottom();
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        steerAirplane();
    }

    private void setInitialAirplaneSpeedAndDirection() {
        airplane.getLinearVelocity()
                .set(.0f, defaultLinearVelocity)
                .limit(airplane.getMaxLinearSpeed());
    }

    private void steerAirplane() {
        float directionX = touchpad.getKnobPercentX();
        float directionY = touchpad.getKnobPercentY();

        if (!MathUtils.isZero(directionX) && !MathUtils.isZero(directionY)) {
            airplane.getLinearVelocity()
                    .set(defaultLinearVelocity * directionX, defaultLinearVelocity * directionY)
                    .limit(airplane.getMaxLinearSpeed());
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
