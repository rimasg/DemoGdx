package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.stack.Block;
import com.sid.demogdx.utils.Box2DUtils;

/**
 * Created by Okis on 2016.06.18 @ 19:32.
 */
public class DropStackScreen extends AbstractBox2dScreen {
    private Body activeBody;
    private Body spawnerBody;
    private Body wallsBody;
    private TextureAtlas.AtlasRegion blockAtlasRegion;

    public DropStackScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        wallsBody = Box2DUtils.createWorldBoundaries(world);
    }



    @Override
    public void render(float delta) {
        super.render(delta);

        updateSpawnerBodyDirection(delta);

        cam.update();
        b2dr.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBlocks();
        game.batch.end();
    }

    @Override
    protected void loadAssets() {
        blockAtlasRegion = Assets.inst().getRegion(RegionNames.HERO);
    }

    @Override
    protected void init() {
        super.init();
//        spawnBlock();
        spawnMovingPlatform();
        createBuoys();
//        createBuoysTop();
    }

    private void spawnMovingPlatform() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(cam.viewportWidth / 2f, cam.viewportHeight * 0.7f);
        spawnerBody = world.createBody(bodyDef);
        spawnerBody.setLinearVelocity(2.0f, 0.f);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.8f, 0.4f);
        spawnerBody.createFixture(polygonShape, 1.0f);
    }

    private void createBuoys() {
        final Body body1 = Box2DUtils.createBox2dCircleBody(world, 2.0f, cam.viewportHeight * 0.3f);
        final Body body2 = Box2DUtils.createBox2dCircleBody(world, 4.0f, cam.viewportHeight * 0.3f);
        final Body body3 = Box2DUtils.createBox2dCircleBody(world, 6.0f, cam.viewportHeight * 0.3f);
        final Body body4 = Box2DUtils.createBox2dCircleBody(world, 8.0f, cam.viewportHeight * 0.3f);

        final Vector2 wallsPos = wallsBody.getPosition();
        wallsPos.add(cam.viewportWidth / 2f, 0.f);
        PrismaticJointDef jointDef = new PrismaticJointDef();

        jointDef.initialize(wallsBody, body1, wallsPos, Vector2.X);
        world.createJoint(jointDef);
        jointDef.initialize(wallsBody, body2, wallsPos, Vector2.Y);
        world.createJoint(jointDef);
        jointDef.initialize(wallsBody, body3, wallsPos, Vector2.Y);
        world.createJoint(jointDef);
        jointDef.initialize(wallsBody, body4, wallsPos, Vector2.Y);
        world.createJoint(jointDef);

        final WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.dampingRatio = 0.f;
        weldJointDef.frequencyHz = 10.0f;
        weldJointDef.initialize(body1, body2, body1.getPosition());
        world.createJoint(weldJointDef);
        weldJointDef.initialize(body2, body3, body2.getPosition());
        world.createJoint(weldJointDef);
        weldJointDef.initialize(body3, body4, body3.getPosition());
        world.createJoint(weldJointDef);
    }

    private void createBuoysTop() {
        final Body body1 = Box2DUtils.createBox2dCircleBody(world, 2.0f, cam.viewportHeight * 0.3f);
        final Body body2 = Box2DUtils.createBox2dCircleBody(world, 4.0f, cam.viewportHeight * 0.3f);
        final Body body3 = Box2DUtils.createBox2dCircleBody(world, 6.0f, cam.viewportHeight * 0.3f);
        final Body body4 = Box2DUtils.createBox2dCircleBody(world, 8.0f, cam.viewportHeight * 0.3f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(cam.viewportWidth / 2f, cam.viewportHeight));
        final Body anchorBody = world.createBody(bodyDef);
//        final Vector2 wallsPos = new Vector2(cam.viewportWidth / 2f, cam.viewportHeight);
//        wallsPos.add(cam.viewportWidth / 2f, 0.f);
//        PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
        DistanceJointDef prismaticJointDef = new DistanceJointDef();
        prismaticJointDef.dampingRatio = 4.0f;
        prismaticJointDef.frequencyHz = 10.0f;
        prismaticJointDef.initialize(anchorBody, body1, anchorBody.getPosition(), body1.getPosition());
        world.createJoint(prismaticJointDef);
        prismaticJointDef.initialize(anchorBody, body2, anchorBody.getPosition(), body2.getPosition());
        world.createJoint(prismaticJointDef);
        prismaticJointDef.initialize(anchorBody, body3, anchorBody.getPosition(), body3.getPosition());
        world.createJoint(prismaticJointDef);
        prismaticJointDef.initialize(anchorBody, body4, anchorBody.getPosition(), body4.getPosition());
        world.createJoint(prismaticJointDef);

        PrismaticJointDef weldJointDef = new PrismaticJointDef();
/*
        weldJointDef.enableLimit = false;
        weldJointDef.upperTranslation = 4.0f;
        weldJointDef.lowerTranslation = 2.0f;
*/
//        weldJointDef.dampingRatio = 4.0f;
//        weldJointDef.frequencyHz = 10.0f;
//        final Vector2 axis = new Vector2(0.0f, 1.0f);
        final Vector2 axis = Vector2.Y;
        weldJointDef.initialize(body1, body2, body2.getPosition(), Vector2.Y);
        world.createJoint(weldJointDef);
        weldJointDef.initialize(body2, body3, body3.getPosition(), Vector2.Y);
        world.createJoint(weldJointDef);
        weldJointDef.initialize(body3, body4, body4.getPosition(), Vector2.Y);
//        world.createJoint(weldJointDef);
//        weldJointDef.initialize(anchorBody, body4, anchorBody.getPosition(), Vector2.Y);
        world.createJoint(weldJointDef);
    }

    private void spawnBlock() {
        final PolygonShape polygonShape = new PolygonShape();
        final float width = MathUtils.random(1.2f, 2.2f);
        final float height = 1.0f;
        polygonShape.setAsBox(width / 2f, height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.f;
        fixtureDef.density = 1.0f;
        activeBody = Box2DUtils.createBox2dBody(world,
                spawnerBody.getPosition().x,
                spawnerBody.getPosition().y,
                fixtureDef, Shape.Type.Polygon);
        activeBody.setUserData(new Block(width, height));
        polygonShape.dispose();
    }

    private void updateSpawnerBodyDirection(float delta) {
        if ((spawnerBody.getPosition().x > cam.viewportWidth - 1.0f) ||
        (spawnerBody.getPosition().x < 1.0f)) {
            spawnerBody.setLinearVelocity(-spawnerBody.getLinearVelocity().x, 0.f);
        }
    }

    private void updateSpawnerBodyHeight() {
        float height = 0.f;
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getType() == BodyDef.BodyType.DynamicBody) {
                // TODO: 2016.06.22 why linear velocity is zero? 
                Gdx.app.log(TAG, "updateSpawnerBodyHeight: linear velocity Y -> " + body.getLinearVelocity().y);
                if ((body.getPosition().y > height)
                        && (body.getPosition().y - 2.0f > spawnerBody.getPosition().y)) {
                    height = body.getPosition().y;
                }
            }
        }
        // TODO: 2016.06.21 spawnerBody should move higher up above the highest body on the stack
        if ((spawnerBody.getPosition().y - height) < 2.0f) {
            spawnerBody.setTransform(
                    spawnerBody.getPosition().x,
                    spawnerBody.getPosition().y + 0.5f,
                    spawnerBody.getAngle());
        }

        Gdx.app.log(TAG, "updateSpawnerBodyHeight: spawnerBody.getPosition().y -> " + spawnerBody.getPosition().y);
        Gdx.app.log(TAG, "updateSpawnerBodyHeight: height -> " + height);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        if (Gdx.input.justTouched()) {
            dropBlock();
        }
    }

    private void drawBlocks() {
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getType() == BodyDef.BodyType.DynamicBody || body.getType() == BodyDef.BodyType.KinematicBody) {
                Block block = null;
                if (body.getUserData() instanceof Block) {
                    block = (Block) body.getUserData();
                    game.batch.draw(blockAtlasRegion,
                            body.getPosition().x - block.width / 2f,
                            body.getPosition().y - block.height / 2f,
                            block.width / 2f, block.height / 2f,
                            block.width, block.height, 1.0f, 1.0f,
                            body.getAngle() * MathUtils.radiansToDegrees );
                } else {
                    game.batch.draw(blockAtlasRegion,
                            body.getPosition().x - 0.8f,
                            body.getPosition().y - 0.4f,
                            0.8f, 0.4f, 0.8f * 2, 0.4f * 2, 1.0f, 1.0f,
                            body.getAngle() * MathUtils.radiansToDegrees );
                }
            }
        }
    }

    private void dropBlock() {
        spawnBlock();
        updateSpawnerBodyHeight();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
    }
}
