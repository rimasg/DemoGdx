package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sid.demogdx.Assets;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.Box2dUtils;

/**
 * Created by Okis on 2016.03.06 @ 20:22.
 */
public class Box2dScreen extends AbstractScreen {
    private static final float SPAWN_BODIES_INTERVAL_SECONDS = 0.2f;
    private static final Vector2 defaultBodyPos = new Vector2(AppConfig.WORLD_WIDTH_VIRTUAL / 2, 20);

    OrthographicCamera cam;
    Viewport viewPort;
    World world;
    Box2DDebugRenderer renderer;
    Array<Body> bodies = new Array<Body>();

    private TextureAtlas.AtlasRegion starRegion;

    public Box2dScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        viewPort = new FitViewport(AppConfig.WORLD_WIDTH_VIRTUAL, AppConfig.WORLD_HEIGHT_VIRTUAL, cam);
        viewPort.apply(true);
        world = new World(new Vector2(0, -9.8f), true);
        renderer = new Box2DDebugRenderer();

        createGround();
//        createRotatingPlatform();
        spawnContinuousBodies();

        final Skin skin = Assets.inst().get("skin.json", Skin.class);
        starRegion = skin.getAtlas().findRegion("star");
    }

    private void createGround() {
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(0, 0), new Vector2(AppConfig.WORLD_WIDTH_VIRTUAL, 0));

        BodyDef bodyDef = new BodyDef();
        Body ground = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        ground.createFixture(fixtureDef);

        edgeShape.dispose();
    }

    private void createRotatingPlatform() {
        PolygonShape polygonShape = new PolygonShape();
//        float[] vertices = {0, -1.0f, -0.2f, -0.2f, -1.0f, 0, -0.2f, 0.2f, 0, 1.0f, 0.2f, 0.2f, 1.0f, 0, 0.2f, -0.2f};
//        polygonShape.set(vertices);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(AppConfig.WORLD_WIDTH_VIRTUAL / 2, AppConfig.WORLD_HEIGHT_VIRTUAL * 0.4f);
        Body platform = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        polygonShape.setAsBox(1.5f, 0.2f);
        fixtureDef.shape = polygonShape;
        platform.createFixture(fixtureDef);
        polygonShape.setAsBox(0.2f, 1.5f);
        fixtureDef.shape = polygonShape;
        platform.createFixture(fixtureDef);

        platform.setAngularVelocity(4.0f);

        polygonShape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        destroyBodiesOutsideWorld();

        cam.update();
        world.step(delta, 6, 2);
        renderer.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBodies(game.batch);
        game.batch.end();
    }

    private Body tmpBody;

    private void spawnContinuousBodies() {
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                tmpBody = Box2dUtils.createBox2dBody(world, defaultBodyPos.x, defaultBodyPos.y);
                tmpBody.applyAngularImpulse(0.2f, true);
            }
        }, 0, SPAWN_BODIES_INTERVAL_SECONDS);
    }

    private void destroyBodiesOutsideWorld() {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getPosition().y < -1.0f) {
                world.destroyBody(body);
            }
        }
    }

    private void drawBodies(SpriteBatch batch) {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getType() == BodyDef.BodyType.DynamicBody) {
                final float radius = body.getFixtureList().get(0).getShape().getRadius();
                batch.draw(starRegion,
                        body.getPosition().x - radius, body.getPosition().y - radius,
                        radius, radius, radius * 2, radius * 2, 1.0f, 1.0f,
                        body.getAngle() * MathUtils.radiansToDegrees);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
    }

    @Override
    public void hide() {
        world.dispose();
    }
}
