package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.interfaces.ListenerClass;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class FallingBallScreen extends AbstractBox2dScreen {
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Box2DMapObjectParser box2DMapObjectParser;

    private Body ball;
    private TextureAtlas.AtlasRegion bodyRegion;
    private TextureAtlas.AtlasRegion starRegion;
    private TextureAtlas.AtlasRegion circleRainbowRegion;

    private ParticleEffect particleEffect;
    private Sound collisionSound;

    private Label scoreLabel;
    private Label timeLabel;

    public FallingBallScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        map = new TmxMapLoader().load("maps/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, Box2DConfig.unitScale32, game.batch);
        box2DMapObjectParser = new Box2DMapObjectParser(mapRenderer.getUnitScale());
//        box2DMapObjectParser.setListener(new Box2DMapObjectParserListenerAdapter(box2DMapObjectParser));
        world.setContactListener(new ListenerClass(){
            @Override
            public void beginContact(Contact contact) {
                super.beginContact(contact);
                final Body bodyA = contact.getFixtureA().getBody();
                final Body bodyB = contact.getFixtureB().getBody();

                if ((isPlayer(bodyA) || isPlayer(bodyB))
                        && ((bodyA.getType() == BodyDef.BodyType.DynamicBody) && bodyB.getType() == BodyDef.BodyType.DynamicBody)) {
                    if (isPlayer(bodyA)) {
                        setParticleToStart(bodyB.getPosition().x, bodyB.getPosition().y);
                        bodiesToRemove.add(bodyB);
                    } else if (isPlayer(bodyB)) {
                        setParticleToStart(bodyA.getPosition().x, bodyA.getPosition().y);
                        bodiesToRemove.add(bodyA);
                    }
                    playCollisionSound();
                }
                if (isFinish(bodyA) || isFinish(bodyB)) {
//                    show();
                }
            }
        });

        loadAssets();
        loadParticles();

        createWorld();
        createPlayer();
        createHUD();
    }

    private void loadAssets() {
        starRegion = Assets.inst().getRegion(RegionNames.STAR);
        circleRainbowRegion = Assets.inst().getRegion(RegionNames.CIRCLE_RAINBOW);
        collisionSound = Assets.inst().getSound(AssetDescriptors.SOUND_COLLISION);
    }

    private void loadParticles() {
        particleEffect = Assets.inst().getParticleEffect(AssetDescriptors.PE_EXPLOSION);
        particleEffect.start();
    }

    private void setParticleToStart(float x, float y) {
        particleEffect.setPosition(x, y);
        particleEffect.start();
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
//        spawnBalls(20, viewport.getWorldWidth() / 2, viewport.getWorldHeight() * 2.0f);
        world.getBodies(bodies);
    }

    private void spawnBalls(int qty, float posX, float posY) {
        for (int i = 0; i < qty; i++) {
            final Body body = Box2DUtils.createBox2dCircleBody(world, MathUtils.random(posX), posY);
            body.setUserData("HangingCircle");
        }
    }

    private void createPlayer() {
        for (Body body : bodies) {
            if (isPlayer(body)) {
                ball = body;
                return;
            }
        }
    }

    private boolean isPlayer(Body body) {
        return body.getUserData() != null && body.getUserData().equals("Player");
    }

    private boolean isFinish(Body body) {
        return body.getUserData() != null && body.getUserData().equals("Finish");
    }

    private void createHUD() {
        final Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        scoreLabel = new Label("", skin, "red");
        scoreLabel.setAlignment(Align.left);
        timeLabel = new Label("", skin, "red");
        timeLabel.setAlignment(Align.right);

        table.row().expand().top();
        table.columnDefaults(0).width(Value.percentWidth(0.25f, table));
        table.add(scoreLabel).left();
        table.add(timeLabel).right();
    }

    private float getMapHeight() {
        final float posY;
        if (map.getProperties().get("height") != null) {
            posY = (int) map.getProperties().get("height");
        } else {
            posY = Box2DConfig.WHV * 2;
        }
        return posY;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        particleEffect.update(delta);
        removedDeadBodies();
        updateHUD();
        // Show only visible part of the Tiled Map on Y-axis - MathUtils.clamp()
        cam.position.set(viewport.getWorldWidth() / 2, MathUtils.clamp(ball.getPosition().y, cam.viewportHeight / 2, cam.viewportHeight * 2), 0);
        cam.update();

//        b2dr.render(world, cam.combined);
        mapRenderer.setView(cam);
        mapRenderer.render();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBodies();
        drawParticles();
        game.batch.end();

        stage.act();
        stage.draw();
    }

    private void removedDeadBodies() {
        if (bodiesToRemove.size > 0) {
            for (Body deadBody : bodiesToRemove) {
                world.destroyBody(deadBody);
            }
            bodiesToRemove.clear();
        }
    }

    private void updateHUD() {
        timeLabel.setText(getScreenTime());
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }

        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            moveBallOnXAxis(-Gdx.input.getAccelerometerX() * 10.f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveBallOnXAxis(-10.0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveBallOnXAxis(10.0f);
        }
    }

    private void moveBallOnXAxis(float x) {
        ball.applyForceToCenter(x, 0, true);
    }

    private void drawBodies() {
        for (Body body : bodies) {
            if ((body.getUserData() != null) && (body.getUserData() instanceof String)) {
                final String userData = (String) body.getUserData();
                switch (userData) {
                    case "HangingCircle":
                        bodyRegion = starRegion;
                        break;
                    case "Player":
                        bodyRegion = circleRainbowRegion;
                        break;
                }

//                final float radius = body.getFixtureList().get(0).getShape().getRadius();
                game.batch.draw(bodyRegion,
                        body.getPosition().x,
                        body.getPosition().y,
                        0, 0, 1, 1, 1, 1,
                        body.getAngle() * MathUtils.radiansToDegrees);
            }
        }
    }

    private void drawParticles() {
        if (!particleEffect.isComplete()) {
            particleEffect.draw(game.batch);
        }
    }

    private void playCollisionSound() {
        collisionSound.play();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        mapRenderer.dispose();
        map.dispose();
    }
}
