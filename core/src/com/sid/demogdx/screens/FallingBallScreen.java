package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
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
        createHUD();
    }

    private void loadAssets() {
        ballRegion = skin.getAtlas().findRegion("star");
    }

    private void createBall() {
        final float posY = getMapHeight();
        ball = Box2dUtils.createBox2dCircleBody(world, AppConfig.WWV / 2, posY);
    }

    private float getMapHeight() {
        final float posY;
        if (map.getProperties().get("height") != null) {
            posY = (int) map.getProperties().get("height");
        } else {
            posY = AppConfig.WHV * 2;
        }
        return posY;
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
    }

    private void createHUD() {
        final Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        final Label labelLeft = new Label("Left", skin, "gold");
        labelLeft.setAlignment(Align.left);
        final Label labelRight = new Label("Right", skin, "gold");
        labelRight.setAlignment(Align.right);

        table.row().expand();
        table.add(labelLeft).top().left().width(200f);
        table.add(labelRight).top().right().width(200f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

//        cam.position.set(viewport.getWorldWidth() / 2, ball.getPosition().y, 0);
        // Show only visible part of the Tiled Map on Y-axis - MathUtils.clamp()
        cam.position.set(viewport.getWorldWidth() / 2, MathUtils.clamp(ball.getPosition().y, cam.viewportHeight / 2, cam.viewportHeight * 2), 0);

        cam.update();

        b2dr.render(world, cam.combined);
        mapRenderer.setView(cam);
        mapRenderer.render();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBall();
        drawBodies();
        game.batch.end();

        stage.act();
        stage.draw();
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
                radius, radius,
                radius * 2f, radius * 2f, 1f, 1f,
                ball.getAngle() * MathUtils.radiansToDegrees);
    }

    private void drawBodies() {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if ((body.getUserData() != null) && "HangingCircle".equals((String) body.getUserData())) {
                final float radius = body.getFixtureList().get(0).getShape().getRadius();
                game.batch.draw(ballRegion,
                        body.getPosition().x,
                        body.getPosition().y,
                        0.5f,
                        0.5f,
                        1f, 1f, 1f, 1f,
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
