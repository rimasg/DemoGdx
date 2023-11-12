package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.entities.SteerableObject;
import com.sid.demogdx.utils.Box2DConfig;
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
    private static final SteerableLocation targetLocation = new SteerableLocation();


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
        airplane.setMaxLinearSpeed(150f);
        airplane.setMaxAngularSpeed(0.2f);
        final Seek<Vector2> airplaneBehavior = new Seek<>(airplane);
        airplaneBehavior.setTarget(targetLocation);
        airplane.setSteeringBehavior(airplaneBehavior);

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
    }

    private void createHud() {
        final Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        touchpad = new Touchpad(2, skin);
        touchpad.setSize(10.f, 10.f);
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

    private static final Vector2 targetDirection = new Vector2();
    private void steerAirplane() {
        float directionX = touchpad.getKnobPercentX();
        float directionY = touchpad.getKnobPercentY();

        if ((directionX <= 0.2f && directionX >= -0.2f) || directionY <= 0.2f && directionY >= -0.2f) {
            return;
        }

        // TODO: 12/11/2023 Requires additional steering improvements for smooth steering
        targetDirection.set(directionX, directionY).scl(1000f).add(airplane.getPosition());
        targetLocation.setPosition(targetDirection);
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
