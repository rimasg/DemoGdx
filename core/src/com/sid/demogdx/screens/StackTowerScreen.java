package com.sid.demogdx.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.UserData;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

public class StackTowerScreen extends AbstractBox2dScreen {

    public StackTowerScreen(DemoGdx game) {
        super(game);
    }

    private final String upperBodyId = "upper";
    private final String lowerBodyId = "lower";
    private Body upperBox;
    private Body lowerBox;

    private final float defaultBoxWidth = 2.0f;
    private final float defaultBoxHeight = 0.5f;
    private boolean isCollision = false;

    private ContactListener contactListener = new ContactListener() {
        @Override
        public void beginContact(Contact contact) {
            final UserData userDataA = (UserData) contact.getFixtureA().getBody().getUserData();
            final UserData userDataB = (UserData) contact.getFixtureB().getBody().getUserData();

            if (userDataA == null || userDataB == null) {
                return;
            }

            if (userDataA.id.equals(upperBodyId) && userDataB.id.equals(lowerBodyId) ||
                    userDataA.id.equals(lowerBodyId) && userDataB.id.equals(upperBodyId)) {
                isCollision = true;
            }
        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    };

    @Override
    public void render(float delta) {
        super.render(delta);

        splitBox();

        cam.update();
        b2dr.render(world, cam.combined);

        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        renderBodyShapes();
        game.shapeRenderer.end();


        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        // TODO: 20/11/2023 Draw something
        game.batch.end();
    }

    private void renderBodyShapes() {
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.circle(upperBox.getPosition().x, upperBox.getPosition().y, 0.2f);
        game.shapeRenderer.circle(lowerBox.getPosition().x, lowerBox.getPosition().y, 0.2f);
    }

    @Override
    protected void loadAssets() {
        // TODO: 20/11/2023 Load assets
    }

    @Override
    protected void init() {
        super.init();

        Box2DUtils.createWorldBoundaries(world);
        createBlocks();
        world.setContactListener(contactListener);
    }

    private void createBlocks() {
        upperBox = Box2DUtils.createBox2dBoxBody(world,
                Box2DConfig.WWV / 2.0f + MathUtils.random(-0.4f * defaultBoxWidth, 0.4f * defaultBoxWidth),
                Box2DConfig.WHV * 0.2f + 1 * defaultBoxHeight * 2.0f * 1.1f,
                defaultBoxWidth, defaultBoxHeight);
        upperBox.setUserData(new UserData(upperBodyId));
        lowerBox = Box2DUtils.createBox2dBoxBody(world,
                Box2DConfig.WWV / 2.0f,
                Box2DConfig.WHV * 0.2f + 0 * defaultBoxHeight * 2.0f * 1.1f,
                defaultBoxWidth, defaultBoxHeight);
        lowerBox.setUserData(new UserData(lowerBodyId));
    }

    private void splitBox() {
        if (!isCollision) {
            return;
        }

        final Vector2 upperBoxPos = upperBox.getPosition();
        final Vector2 lowerBoxPos = lowerBox.getPosition();
        final float deltaXPos = upperBoxPos.x - lowerBoxPos.x;
        final float shiftRatio = Math.abs(deltaXPos) / defaultBoxWidth;

        float box1StartXPos;
        float box1Width;
        float box2StartXPos;
        float box2Width;

        // TODO: 27/11/2023 implement correct body split
        if (deltaXPos < 0) {
            box1StartXPos = upperBoxPos.x - Math.abs(deltaXPos);
            box1Width = defaultBoxWidth * shiftRatio;
            box2StartXPos = upperBoxPos.x + Math.abs(deltaXPos);
            box2Width = defaultBoxWidth * (1f - shiftRatio);
        } else {
            box1StartXPos = upperBoxPos.x - Math.abs(deltaXPos);
            box1Width = defaultBoxWidth * (1f - shiftRatio);
            box2StartXPos = upperBoxPos.x + Math.abs(deltaXPos);
            box2Width = defaultBoxWidth * shiftRatio;
        }

        world.destroyBody(upperBox);
        Box2DUtils.createBox2dBoxBody(world, box1StartXPos, upperBoxPos.y, box1Width, defaultBoxHeight);
        Box2DUtils.createBox2dBoxBody(world, box2StartXPos, upperBoxPos.y, box2Width, defaultBoxHeight);

        isCollision = false;
    }
}
