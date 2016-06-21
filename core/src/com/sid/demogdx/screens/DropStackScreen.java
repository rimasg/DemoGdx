package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.stack.Block;
import com.sid.demogdx.utils.Box2dUtils;

/**
 * Created by Okis on 2016.06.18 @ 19:32.
 */
public class DropStackScreen extends AbstractBox2dScreen {
    private Body activeBody;
    private Body spawnerBody;
    private TextureAtlas.AtlasRegion blockAtlasRegion;

    public DropStackScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Box2dUtils.createWorldBoundaries(world);
//        spawnBlock();
        spawnMovingPlatform();
        loadAssets();
    }

    private void spawnMovingPlatform() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(cam.viewportWidth / 2, cam.viewportHeight * 0.8f);
        spawnerBody = world.createBody(bodyDef);
        spawnerBody.setLinearVelocity(2.0f, 0.f);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.8f, 0.4f);
        spawnerBody.createFixture(polygonShape, 1.0f);
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
        activeBody = Box2dUtils.createBox2dBody(world,
                spawnerBody.getPosition().x,
                spawnerBody.getPosition().y,
                fixtureDef, Shape.Type.Polygon);
        activeBody.setUserData(new Block(width, height));
        polygonShape.dispose();
    }

    private void loadAssets() {
        blockAtlasRegion = skin.getAtlas().findRegion("button_gold");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        updateSpawnerBody(delta);

        cam.update();
        b2dr.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBlocks();
        game.batch.end();
    }

    private void updateSpawnerBody(float delta) {
        if ((spawnerBody.getPosition().x > cam.viewportWidth - 1.0f) ||
        (spawnerBody.getPosition().x < 1.0f)) {
            spawnerBody.setLinearVelocity(-spawnerBody.getLinearVelocity().x, 0.f);
        }
    }

    private void handleInput() {
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
        if (activeBody != null) {
            activeBody.getFixtureList().get(0).setDensity(1.0f);
        }
        spawnBlock();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
