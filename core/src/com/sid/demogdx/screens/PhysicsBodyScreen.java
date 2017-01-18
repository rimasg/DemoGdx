package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2017.01.14.
 */
// TODO: 2017.01.14 consider removing this Class
public class PhysicsBodyScreen extends AbstractBox2dScreen {

    public static final float ROCKET_SIZE = 2.0f;
    private Body rocketBody;
    private Vector2 rocketOrigin;
    private Vector2 rocketPos;
    private TextureAtlas.AtlasRegion rocketAtlasRegion;
    private TiledMap map;

    public PhysicsBodyScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        initPhysicsBody();
        rocketAtlasRegion = skin.getAtlas().findRegion("rocket");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        drawPhysicsBody();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
    }

    private void initPhysicsBody() {
//        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("physics_body/rocket.json"));
        final Body worldBody = Box2DUtils.createWorldBoundaries(world);

        map = new TmxMapLoader().load("maps/physics_map.tmx");
        new Box2DMapObjectParser(Box2DConfig.unitScale32).load(world, map);

        world.getBodies(bodiesToRemove);

/*
        for (float i = 1.f; i < 5.f; i += 0.5f) {
            Box2DUtils.createBox2dBody(world, cam.viewportWidth / 2.f, cam.viewportHeight * 0.1f + i, null, Shape.Type.Polygon);
        }
*/

/*
        final Shape circleShape = new CircleShape();
        final PolygonShape polyShape = new PolygonShape();

        final BodyDef bdef = new BodyDef();
        bdef.position.set(cam.viewportWidth / 2.f, cam.viewportHeight / 2.f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        rocketBody = world.createBody(bdef);

        final FixtureDef fdef = new FixtureDef();
        fdef.density = 1.f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.6f;

        fdef.shape = circleShape;
        circleShape.setRadius(0.5f);
        rocketBody.createFixture(fdef);
        fdef.shape = polyShape;
        polyShape.setAsBox(0.5f, 0.25f, new Vector2(1.f, 0.f), 0.f * MathUtils.degRad);
        rocketBody.createFixture(fdef);
        polyShape.setAsBox(0.5f, 0.25f, new Vector2(-1.f, 0.f), 0.f * MathUtils.degRad);
        rocketBody.createFixture(fdef);

        circleShape.dispose();
        polyShape.dispose();
*/

//        loader.attachFixture(rocketBody, "Rocket", fdef, ROCKET_SIZE);
//        rocketOrigin = loader.getOrigin("Rocket", ROCKET_SIZE).cpy();
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rocketBody.applyForceToCenter(-10.f, 0.f, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rocketBody.applyForceToCenter(10.f, 0.f, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            rocketBody.applyForceToCenter(0.f, 10.f, true);
        }
    }

    private void drawPhysicsBody() {
        b2dr.render(world, cam.combined);

/*
        rocketPos = rocketBody.getPosition().sub(rocketOrigin);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(rocketAtlasRegion,
                rocketPos.x,
                rocketPos.y,
                rocketOrigin.x,
                rocketOrigin.y,
                ROCKET_SIZE, ROCKET_SIZE, 1.f, 1.f, rocketBody.getAngle() * MathUtils.radiansToDegrees);
        game.batch.end();
*/
    }
}
