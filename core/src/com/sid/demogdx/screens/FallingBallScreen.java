package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.Box2dUtils;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class FallingBallScreen extends AbstractBox2dScreen {
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Box2DMapObjectParser box2DMapObjectParser;

    private Body ball;
    private TextureAtlas.AtlasRegion ballRegion;

    public FallingBallScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        map = new TmxMapLoader().load("maps/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, AppConfig.unitScale32, game.batch);
        box2DMapObjectParser = new Box2DMapObjectParser(mapRenderer.getUnitScale());

        loadAssets();

        createBall();
        createWorld();
    }

    private void loadAssets() {
        ballRegion = skin.getAtlas().findRegion("star");
    }

    private void createBall() {
        final float posY;
        if (map.getProperties().get("height") != null) {
            posY = (int) map.getProperties().get("height");
        } else {
            posY = AppConfig.WHV * 2;
        }
        ball = Box2dUtils.createBox2dCircleBody(world, AppConfig.WWV / 2, posY);
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        cam.position.set(viewport.getWorldWidth() / 2, ball.getPosition().y, 0);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        b2dr.render(world, cam.combined);
        mapRenderer.setView(cam);
        mapRenderer.render();
        game.batch.begin();
        drawBall();
        drawBodies();
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            ball.applyForceToCenter(-Gdx.input.getAccelerometerX(), 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private void drawBall() {
        final float radius = ball.getFixtureList().get(0).getShape().getRadius();
        game.batch.draw(ballRegion,
                ball.getPosition().x - radius,
                ball.getPosition().y - radius,
                radius,
                radius,
                radius * 2, radius * 2, 1, 1,
                ball.getAngle() * MathUtils.radiansToDegrees);
    }

    private void drawBodies() {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if ((body.getUserData() != null) && ((String) body.getUserData()).equals("HangingCircle")) {
                final float radius = body.getFixtureList().get(0).getShape().getRadius();
                game.batch.draw(ballRegion,
                        body.getPosition().x,
                        body.getPosition().y,
                        radius,
                        radius,
                        radius * 2, radius * 2, 1, 1,
                        body.getAngle() * MathUtils.radiansToDegrees);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        mapRenderer.dispose();
        map.dispose();
    }
}
