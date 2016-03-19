package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sid.demogdx.Assets;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.Box2dUtils;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class FallingBallScreen extends AbstractScreen {
    private final OrthographicCamera cam = new OrthographicCamera();
    Viewport viewport;
    World world;
    Box2DDebugRenderer b2dr;

    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Box2DMapObjectParser box2DMapObjectParser;

    private Body ball;
    private Array<Body> bodies = new Array<Body>();
    private TextureAtlas.AtlasRegion ballRegion;

    public FallingBallScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        viewport = new FitViewport(AppConfig.WORLD_WIDTH_VIRTUAL, AppConfig.WORLD_HEIGHT_VIRTUAL, cam);
        viewport.apply(true);
        world = new World(new Vector2(0, -4.9f), true);
        b2dr = new Box2DDebugRenderer();
        //
        map = new TmxMapLoader().load("maps/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, AppConfig.unitScale32, game.batch);
        box2DMapObjectParser = new Box2DMapObjectParser(mapRenderer.getUnitScale());
        //
        loadAssets();
        //
        createBall();
        createWorld();
    }

    private void loadAssets() {
        final Skin skin = Assets.inst().get("skin.json", Skin.class);
        ballRegion = skin.getAtlas().findRegion("star");
    }

    private void createBall() {
        final float posY;
        if (map.getProperties().get("height") != null) {
            posY = (int) map.getProperties().get("height");
        } else {
            posY = AppConfig.WORLD_HEIGHT_VIRTUAL * 2;
        }
        ball = Box2dUtils.createBox2dCircleBody(world,
                AppConfig.WORLD_WIDTH_VIRTUAL / 2,
                posY);
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        //
        world.step(delta, 6, 2);
        cam.position.set(viewport.getWorldWidth() / 2, ball.getPosition().y, 0);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        //
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
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        b2dr.dispose();
        mapRenderer.dispose();
        map.dispose();
        world.dispose();
    }
}
