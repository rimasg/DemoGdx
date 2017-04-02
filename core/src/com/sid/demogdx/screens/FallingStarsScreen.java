package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

/**
 * Created by Okis on 2016.03.06 @ 20:22.
 */
public class FallingStarsScreen extends AbstractBox2dScreen {
    private static final float SPAWN_BODIES_INTERVAL_SECONDS = 0.4f;
    private static final Vector2 defaultBodyPos = new Vector2(Box2DConfig.WWV / 2, 20);

    private ParticleEffect particleEffect;
    private ParticleEffectPool particleEffectPool;
    private ParticleEffectPool.PooledEffect pooledEffect;

    private TextureAtlas.AtlasRegion starRegion;
    private TextureAtlas.AtlasRegion lineDotRegion;
    private Body ground;

    public FallingStarsScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        createGround();
//        createRotatingPlatform();
//        createJointBodies();
        spawnContinuousBodies();

        starRegion = Assets.inst().getRegion(RegionNames.STAR);
        lineDotRegion = Assets.inst().getRegion(RegionNames.LINE_DOT);

        particleEffect = Assets.inst().getParticleEffect(AssetDescriptors.PE_STAR_TRAIL);
        particleEffectPool = new ParticleEffectPool(particleEffect, 20, 100);
    }

    private void createGround() {
        EdgeShape floor = new EdgeShape();
        floor.set(new Vector2(0, 0), new Vector2(Box2DConfig.WWV, 0));

        BodyDef bodyDef = new BodyDef();
        ground = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floor;
        ground.createFixture(fixtureDef);

        floor.dispose();
    }

    private void createRotatingPlatform() {
        PolygonShape polygonShape = new PolygonShape();
//        float[] vertices = {0, -1.0f, -0.2f, -0.2f, -1.0f, 0, -0.2f, 0.2f, 0, 1.0f, 0.2f, 0.2f, 1.0f, 0, 0.2f, -0.2f};
//        polygonShape.set(vertices);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Box2DConfig.WWV / 2, Box2DConfig.WHV * 0.2f);
        Body platform = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        polygonShape.setAsBox(1.5f, 0.2f);
        fixtureDef.shape = polygonShape;
        platform.createFixture(fixtureDef);
        polygonShape.setAsBox(0.2f, 1.5f);
        fixtureDef.shape = polygonShape;
        platform.createFixture(fixtureDef);

        platform.setAngularVelocity(4.0f);
        platform.setUserData(BodyType.PLATFORM);

        polygonShape.dispose();
    }

    private void createJointBodies() {
        BodyDef bd = new BodyDef();
        FixtureDef fd = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        bd.type = BodyDef.BodyType.DynamicBody;
        fd.shape = polygonShape;
        fd.density = 10.0f;

        bd.position.set(Box2DConfig.WWV / 2, Box2DConfig.WHV * 0.4f);
        final Body bodyA = world.createBody(bd);
        bd.position.set(bodyA.getPosition().x + 1.0f, bodyA.getPosition().y - 1.0f);
        final Body bodyB = world.createBody(bd);

        polygonShape.setAsBox(1.0f, 0.5f);
        bodyA.createFixture(fd);

        polygonShape.setAsBox(0.5f, 1.0f);
        bodyB.createFixture(fd);

        polygonShape.dispose();

        // Joints

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.collideConnected  = false;

        revoluteJointDef.initialize(ground, bodyA, new Vector2(Box2DConfig.WWV / 2, Box2DConfig.WHV * 0.5f));
        revoluteJointDef.localAnchorB.set(-1.0f, 0);
        world.createJoint(revoluteJointDef);
        revoluteJointDef.initialize(bodyA, bodyB, new Vector2());
        revoluteJointDef.localAnchorA.set(1.0f, 0.0f);
        revoluteJointDef.localAnchorB.set(0.0f, 1.0f);
        world.createJoint(revoluteJointDef);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        destroyBodiesOutsideWorld();

        cam.update();
        // TODO: 2016.03.24 uncomment
//        b2dr.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBodies(delta);
//        drawRotatingPlatform(game.batch);
        game.batch.end();
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private Body tmpBody;

    private void spawnContinuousBodies() {
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                tmpBody = Box2DUtils.createBox2dCircleBody(world, defaultBodyPos.x + MathUtils.random(-defaultBodyPos.x * 0.2f, defaultBodyPos.x * 0.2f), defaultBodyPos.y);
                tmpBody.applyAngularImpulse(0.2f, true);
                addParticleEffectToBody(tmpBody);
            }
        }, 0, SPAWN_BODIES_INTERVAL_SECONDS);
    }

    private void addParticleEffectToBody(Body body) {
        body.setUserData(particleEffectPool.obtain());
    }

    private void destroyBodiesOutsideWorld() {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getPosition().y < -2.0f) {
                removeParticleEffectFromBody(body);
                world.destroyBody(body);
            }
        }
    }

    private void removeParticleEffectFromBody(Body body) {
        if (body.getUserData() instanceof ParticleEffectPool.PooledEffect) {
            ((ParticleEffectPool.PooledEffect) body.getUserData()).free();
        }
    }

    private void drawBodies(float delta) {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getType() == BodyDef.BodyType.DynamicBody) {
                final float radius = body.getFixtureList().get(0).getShape().getRadius();
                game.batch.draw(starRegion,
                        body.getPosition().x - radius, body.getPosition().y - radius,
                        radius, radius, radius * 2, radius * 2, 1.0f, 1.0f,
                        body.getAngle() * MathUtils.radiansToDegrees);
                drawParticles(body, delta);
            }
        }
    }

    private void drawParticles(Body body, float delta) {
        if (body.getUserData() instanceof ParticleEffectPool.PooledEffect) {
            pooledEffect = (ParticleEffectPool.PooledEffect) body.getUserData();
            pooledEffect.setPosition(body.getPosition().x, body.getPosition().y);
            pooledEffect.draw(game.batch, delta);
        }
    }

    private void drawRotatingPlatform(SpriteBatch batch) {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (BodyType.PLATFORM == (BodyType) body.getUserData()) {
                final Array<Fixture> fixtureList = body.getFixtureList();
                for (Fixture fixture : fixtureList) {
                    // TODO: 2016.03.08 find NOT Body angle but Fixture angle
                    final Vector2 position = net.dermetfan.gdx.physics.box2d.Box2DUtils.position(fixture);
                    batch.draw(lineDotRegion, position.x - 1, position.y,
                            1.0f, 0.25f, 2.0f, 0.5f, 1.0f, 1.0f, body.getAngle() * MathUtils.radiansToDegrees);
                }

            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    private enum BodyType{
        STAR, PLATFORM
    }
}
