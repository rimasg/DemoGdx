package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.Box2dUtils;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class FallingBallScreen extends AbstractScreen {
    private final OrthographicCamera cam = new OrthographicCamera();
    Viewport viewPort;
    World world;
    Box2DDebugRenderer b2dr;

    Body ball;

    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Box2DMapObjectParser box2DMapObjectParser;

    public FallingBallScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        viewPort = new FitViewport(AppConfig.WORLD_WIDTH_VIRTUAL, AppConfig.WORLD_HEIGHT_VIRTUAL, cam);
        viewPort.apply(true);
        world = new World(new Vector2(0, -9.8f), true);
        b2dr = new Box2DDebugRenderer();
        //
        map = new TmxMapLoader().load("maps/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, AppConfig.unitScale32, game.batch);
        box2DMapObjectParser = new Box2DMapObjectParser(AppConfig.unitScale32);
        //
        createBall();
        createWorld();
    }

    private void createBall() {
        ball = Box2dUtils.createBox2dCircleBody(world,
                AppConfig.WORLD_WIDTH_VIRTUAL / 2,
                AppConfig.WORLD_HEIGHT_VIRTUAL * 2.0f);
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);

/*
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(viewPort.getWorldWidth() / 2, 0);
        final Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4, 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        shape.dispose();
*/
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        world.step(delta, 6, 2);
        //
        cam.position.set(viewPort.getWorldWidth() / 2, ball.getPosition().y, 0);
        cam.update();
        b2dr.render(world, cam.combined);
        //
        mapRenderer.setView(cam);
        mapRenderer.render();
    }

    private void handleInput() {
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            ball.applyForceToCenter(-Gdx.input.getAccelerometerX(), 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
    }

    @Override
    public void hide() {
        b2dr.dispose();
        mapRenderer.dispose();
        world.dispose();
        map.dispose();
    }
}
