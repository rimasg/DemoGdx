package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
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
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

import java.util.Locale;

public class StackTowerScreen extends AbstractBox2dScreen {

    public StackTowerScreen(DemoGdx game) {
        super(game);
    }

    private Body upperBox;
    private Body lowerBox;
    private final float defaultBoxWidth = 4.0f;
    private final float minBoxWidth = 0.12f;
    private float lastBoxWidth = defaultBoxWidth;
    private final float defaultBoxHeight = 0.5f;
    private boolean isCollision = false;

    private final ContactListener contactListener = new ContactListener() {
        // NOTE: You cannot create/destroy Box2D entities inside these callbacks. See: https://box2d.org/documentation/classb2_contact_listener.html
        @Override
        public void beginContact(Contact contact) {
            final Body bodyA = contact.getFixtureA().getBody();
            final Body bodyB = contact.getFixtureB().getBody();

            if (!checkUpperAndLowerBodiesCollide(bodyA, bodyB)) {
                return;
            }

            isCollision = true;
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

    private boolean checkUpperAndLowerBodiesCollide(Body bodyA, Body bodyB) {
        return (bodyA == lowerBox && bodyB == upperBox) || (bodyA == upperBox && bodyB == lowerBox);
    }

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
        if (upperBox != null) {
            game.shapeRenderer.setColor(Color.RED);
            game.shapeRenderer.circle(upperBox.getPosition().x, upperBox.getPosition().y, 0.2f);
        }
        if (lowerBox != null) {
            game.shapeRenderer.setColor(Color.GREEN);
            game.shapeRenderer.circle(lowerBox.getPosition().x, lowerBox.getPosition().y, 0.2f);
        }
    }

    @Override
    protected void loadAssets() {
        // TODO: 20/11/2023 Load assets
    }

    @Override
    protected void init() {
        super.init();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    createBlock();
                }
                return super.keyUp(keycode);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                createBlock();
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });

        Box2DUtils.createWorldBoundaries(world);
        createBlock();
        world.setContactListener(contactListener);
    }

    private void createBlock() {
        if (isCollision) {
            return;
        }
        // FIXME: 13/03/2024
        //  Game crashes when blocks are small. It could be that we create a body of size 0.
        //  Error message:
        //  > b2PolygonShape.cpp, Line 430
        //  > Expression: area > 1.19209289550781250000000000000000000e-7F
        //  See: https://github.com/libgdx/libgdx/issues/2936#issuecomment-80269209
        //  Check for min size when create a new box, e.g. minBoxWidth
        //  Or maybe something with collision handling or body destruction?
        lastBoxWidth = Math.max(minBoxWidth, lastBoxWidth);

        float dropPosY;
        if (lowerBox != null) {
            dropPosY = lowerBox.getPosition().y;
        } else if (upperBox != null) {
            dropPosY = upperBox.getPosition().y;
        } else {
            dropPosY = Box2DConfig.WHV * 0.2f;
        }

        // TODO: 26/02/2024 Do not create a new block if lower block has fallen down

        if (lowerBox == null) {
            lowerBox = upperBox;
        }

        Gdx.app.log("", "Box width: " + lastBoxWidth);

        upperBox = Box2DUtils.createBox2dBoxBody(world,
                Box2DConfig.WWV / 2.0f + MathUtils.random(-0.4f * lastBoxWidth, 0.4f * lastBoxWidth),
                dropPosY + defaultBoxHeight * 4f,
                lastBoxWidth, defaultBoxHeight);
    }

    private void splitBox() {
        if (!isCollision) {
            return;
        }
        isCollision = false;

        final Vector2 upperBoxPos = upperBox.getPosition();
        final Vector2 lowerBoxPos = lowerBox.getPosition();

        if (upperBoxPos.y < lowerBoxPos.y) {
            Gdx.app.log("", "Upper box can't be below Lower box");
            return;
        }

        final float deltaPosX = upperBoxPos.x - lowerBoxPos.x;
        final float deltaPosXAbs = Math.abs(deltaPosX);
        final float shiftRatio = deltaPosXAbs / lastBoxWidth;

        float box1StartPosX;
        float box1Width;
        float box2StartPosX;
        float box2Width;

        if (deltaPosX < 0) {
            box1Width = lastBoxWidth * shiftRatio;
            box1StartPosX = lowerBoxPos.x - lastBoxWidth / 2f - box1Width / 2f;
            box2Width = lastBoxWidth * (1f - shiftRatio);
            box2StartPosX = lowerBoxPos.x - lastBoxWidth / 2f + box2Width / 2f;
            lastBoxWidth = box2Width;
        } else {
            box1Width = lastBoxWidth * (1f - shiftRatio);
            box1StartPosX = lowerBoxPos.x + lastBoxWidth / 2f - box1Width / 2f;
            box2Width = lastBoxWidth * shiftRatio;
            box2StartPosX = lowerBoxPos.x + lastBoxWidth / 2f + box2Width / 2f;
            lastBoxWidth = box1Width;
        }

        // TODO: 13/03/2024 Not sure if we need to set upperBox to null.
        world.destroyBody(upperBox);
        upperBox = null;

        final Body box1Body = Box2DUtils.createBox2dBoxBody(world, box1StartPosX, upperBoxPos.y, box1Width,
                defaultBoxHeight);
        final Body box2Body = Box2DUtils.createBox2dBoxBody(world, box2StartPosX, upperBoxPos.y, box2Width,
                defaultBoxHeight);

        if (deltaPosX < 0) {
            lowerBox = box2Body;
        } else {
            lowerBox = box1Body;
        }
    }

    private String getTimestamp() {
        long milliseconds = System.currentTimeMillis();
        return String.format(Locale.getDefault(), "%1$tY-%1$tm-%1$td %1$tT", milliseconds);
    }

    @Override
    public void hide() {
        super.hide();

        upperBox = null;
        lowerBox = null;
        lastBoxWidth = defaultBoxWidth;
    }
}
