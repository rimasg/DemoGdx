package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.utils.Box2dUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class GravityBallsScreen extends AbstractBox2dScreen {

    private ParticleEffect particleEffect;
    private ParticleEffectPool particleEffectPool;
    private Array<ParticleEffectPool.PooledEffect> effects = new Array<>();

    private Sound collisionSound;

    private Array<TextureAtlas.AtlasRegion> ballsRegions = new Array<>();

    public GravityBallsScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        world.setGravity(new Vector2(-2.0f, -30.0f));

        loadAssets();
        loadParticles();

        createWorld();
        createHUD();
        //
        spawnBalls(94);
        world.getBodies(bodies);
        //
        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cam.unproject(touchPos.set(screenX, screenY, 0));
                hitBody = null;
                world.QueryAABB(callback, touchPos.x - 0.0001f, touchPos.y - 0.0001f, touchPos.x + 0.0001f, touchPos.y + 0.0001f);
                if (hitBody == null) {
                    return false;
                }

                if (hitBody.getType() == BodyDef.BodyType.DynamicBody) {
                    markBodiesForRemoval();
                }
                return false;
            }
        };

        Gdx.input.setInputProcessor(inputProcessor);
    }

    private void markBodiesForRemoval() {
        final Queue<Body> toExplore = new LinkedList<>();
        final Set<Body> visited = new HashSet<>();
        Body currentBall;
        int ballType = (int) hitBody.getUserData();

        bodiesToRemove.add(hitBody);
        setParticleToStart(hitBody); /* set particles */
        toExplore.add(hitBody);
        while (!toExplore.isEmpty()) {
            currentBall = toExplore.poll();
            if (!visited.contains(currentBall)) {
                visited.add(currentBall);
                for (Body neighbourBall : bodies) {
                    if (neighbourBall == currentBall) continue;
                    if (visited.contains(neighbourBall)) continue;
                    if (neighbourBall.getType() != BodyDef.BodyType.DynamicBody) continue;

                    final float radiusSum = (AppConfig.BALL_RADIUS + AppConfig.BALL_RADIUS) + 0.1f; /* overlap proximity  */
                    if (neighbourBall.getPosition().dst2(currentBall.getPosition()) < radiusSum * radiusSum) {
                        if (ballType == (int) neighbourBall.getUserData()) {
                            if (!bodiesToRemove.contains(neighbourBall, true)) {
                                bodiesToRemove.add(neighbourBall);
                                setParticleToStart(neighbourBall); /* set particles */
                            }
                            toExplore.add(neighbourBall);
                        } else {
                            visited.add(neighbourBall);
                        }
                    }
                }
            }
        }
    }

    private void loadAssets() {
        loadBallsRegions();
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.ogg"));
    }

    private void loadBallsRegions() {
        if (ballsRegions.size > 0) return; /* skip loading assets if they're loaded */
        ballsRegions.add(skin.getAtlas().findRegion("red_ball_border"));
        ballsRegions.add(skin.getAtlas().findRegion("green_ball_border"));
        ballsRegions.add(skin.getAtlas().findRegion("blue_ball_border"));
        ballsRegions.add(skin.getAtlas().findRegion("yellow_ball_border"));
        ballsRegions.add(skin.getAtlas().findRegion("cyan_ball_border"));
        ballsRegions.add(skin.getAtlas().findRegion("magenta_ball_border"));
    }

    private void loadParticles() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/gravity_explosion.p"), Gdx.files.internal("textures"));
        particleEffectPool = new ParticleEffectPool(particleEffect, 20, 100);
    }

    private void setParticleToStart(Body body) {
        final ParticleEffectPool.PooledEffect pooledEffect = particleEffectPool.obtain();
        pooledEffect.setPosition(body.getPosition().x, body.getPosition().y);
        effects.add(pooledEffect);
    }

    private void drawParticles(SpriteBatch batch, float delta) {
        for (int i = effects.size - 1; i >= 0; i--) {
            final ParticleEffectPool.PooledEffect pooledEffect = effects.get(i);
            pooledEffect.draw(batch, delta);
            if (pooledEffect.isComplete()) {
                pooledEffect.free();
                effects.removeIndex(i);
            }
        }
    }

    private void createWorld() {
        EdgeShape shape = new EdgeShape();

        BodyDef bodyDef = new BodyDef();
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;

        shape.set(new Vector2(0, 0), new Vector2(AppConfig.WWV, 0)); /* ground */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(0.0001f, 0), new Vector2(0, AppConfig.WHV)); /* left side */
        body.createFixture(fixtureDef);
        shape.set(new Vector2(AppConfig.WWV, 0), new Vector2(AppConfig.WWV, AppConfig.WHV)); /* right side */
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    private void spawnBalls(int qty) {
        final float ballRadius = AppConfig.BALL_RADIUS;
        final float ballSize = ballRadius * 2;
        int cols = MathUtils.floor(cam.viewportWidth / ballSize);
        float col = ballRadius;
        float row = ballRadius;
        //
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        fixtureDef.density = 1.0f;
        //
        for (int i = 0; i < qty; i++) {
            if (col > cols) {
                if ((row - ballRadius) % 2 == 0) {
                    col = ballSize;
                    cols--;
                } else {
                    col = ballRadius;
                    cols++;
                }
                row += ballSize;
            }
            final Body body = Box2dUtils.createBox2dCircleBody(world, col, row, fixtureDef);
            col += ballSize;

            final int randomBallColor = MathUtils.random(ballsRegions.size - 1);
            body.setUserData(randomBallColor);
        }
    }

    private void createHUD() {
        final Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        removeDeadBodies();
        cam.update();

        b2dr.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        drawBodies();
        drawParticles(game.batch, delta);
        game.batch.end();

        stage.act();
        stage.draw();
    }

    private void removeDeadBodies() {
        if (bodiesToRemove.size > 0) {
            for (Body deadBody : bodiesToRemove) {
                world.destroyBody(deadBody);
            }
            bodiesToRemove.clear();
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private void drawBodies() {
        for (Body body : bodies) {
            if ((body.getUserData() != null) && (body.getUserData() instanceof Integer)) {
                final int ballType = (int) body.getUserData();
                game.batch.draw(ballsRegions.get(ballType),
                        body.getPosition().x - 0.5f,
                        body.getPosition().y - 0.5f,
                        0.5f, 0.5f, 1, 1, 1, 1,
                        body.getAngle() * MathUtils.radiansToDegrees);
            }
        }
    }

    private void resetVars() {

    }

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(touchPos.x, touchPos.y)) {
                hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        resetVars();
        collisionSound.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
