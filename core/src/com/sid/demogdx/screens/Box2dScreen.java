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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
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
    private static final long delayTime = 100_000_000L;

    OrthographicCamera cam;
    Viewport viewPort;
    World world;
    Box2DDebugRenderer renderer;
    Array<Body> bodies = new Array<Body>();

    private long startTime = TimeUtils.nanoTime();
    private TextureAtlas.AtlasRegion starRegion;

    public Box2dScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        viewPort = new FitViewport(AppConfig.VIRTUAL_WORLD_WIDTH, AppConfig.VIRTUAL_WORLD_HEIGHT, cam);
        viewPort.apply(true);
        world = new World(new Vector2(0, -9.8f), true);
        renderer = new Box2DDebugRenderer();

        createGround();

        final Skin skin = Assets.inst().get("skin.json", Skin.class);
        starRegion = skin.getAtlas().findRegion("star");
    }

    private void createGround() {
        Body ground;
        BodyDef bodyDef = new BodyDef();
        ground = world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(0, 0), new Vector2(AppConfig.VIRTUAL_WORLD_WIDTH, 0));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        ground.createFixture(fixtureDef);

        edgeShape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spawnBodies();
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

    private void spawnBodies() {
        if (TimeUtils.nanoTime() > (startTime + delayTime)) {
//            tmpBody = Box2dUtils.createBox2dBody(world, MathUtils.random(AppConfig.VIRTUAL_WORLD_WIDTH), 20);
            tmpBody = Box2dUtils.createBox2dBody(world, 4.5f, 20);
            tmpBody.applyAngularImpulse(0.2f, true);
            startTime = TimeUtils.nanoTime();
        }
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
                batch.draw(starRegion, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f,
                        0.5f, 0.5f, 1.0f, 1.0f, 1.0f, 1.0f, body.getAngle() * MathUtils.radiansToDegrees);
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
