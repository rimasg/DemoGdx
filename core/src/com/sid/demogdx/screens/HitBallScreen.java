package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.interfaces.ListenerClass;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.ProjectileMotion;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;
import net.dermetfan.gdx.physics.box2d.Chain;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class HitBallScreen extends AbstractBox2dScreen {
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Box2DMapObjectParser box2DMapObjectParser;

    private Body player;
    private Body finish;
    private Chain chain;
    private TextureAtlas.AtlasRegion bodyRegion;
    private TextureAtlas.AtlasRegion starRegion;
    private TextureAtlas.AtlasRegion blockRegion;
    private TextureAtlas.AtlasRegion circleRainbowRegion;

    private ParticleEffect particleEffect;
    private Sound collisionSound;

    private Label scoreLabel;
    private Label timeLabel;

    private Vector2 impulse = new Vector2();
    private boolean touchedPlayer = false;
    private Array<Vector2> displacement;

    public HitBallScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        map = new TmxMapLoader().load("maps/hit_ball_map.tmx");
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

                    } else if (isPlayer(bodyB)) {
                    }
//                    playCollisionSound();
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
        createChainHolder();
        chain = createChain();
        attachChainToChainHolder();
        createHUD();
        // TODO: 2016.10.03 write code to hit a player
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cam.unproject(touchPos.set(screenX, screenY, 0));
                world.QueryAABB(callback, touchPos.x - 0.0001f, touchPos.y - 0.0001f, touchPos.x + 0.0001f, touchPos.y + 0.0001f);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (touchedPlayer) {
                    cam.unproject(touchPos.set(screenX, screenY, 0));
                    impulse.set(
                            player.getPosition().x - touchPos.x,
                            player.getPosition().y - touchPos.y);
                    displacement = ProjectileMotion.calcDisplacement(player.getPosition(), new Vector2(touchPos.x, touchPos.y), impulse);
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (touchedPlayer) {
                    // cam.unproject(touchPos.set(screenX, screenY, 0));
                    impulse.scl(ProjectileMotion.INITIAL_VELOCITY); // Impulse force
                    player.applyLinearImpulse(impulse, player.getPosition(), true);
                }
                touchedPlayer = false;
                return false;
            }
        });
    }

    private void loadAssets() {
        starRegion = skin.getAtlas().findRegion("star");
        circleRainbowRegion = skin.getAtlas().findRegion("circle_rainbow");
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.ogg"));
    }

    private void loadParticles() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/explosion.p"), Gdx.files.internal("textures"));
        particleEffect.start();
    }

    private void setParticleToStart(float x, float y) {
        particleEffect.setPosition(x, y);
        particleEffect.start();
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
        world.getBodies(bodies);
    }

    private void createPlayer() {
        for (Body body : bodies) {
            if (isPlayer(body)) {
                player = body;
                return;
            }
        }
    }

    private void createChainHolder() {
        for (Body body : bodies) {
            if (isFinish(body)) {
                finish = body;
                return;
            }
        }
    }

    private Chain createChain() {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(finish.getPosition());

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(.1f, 0.25f);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.0f;

        final RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.localAnchorA.y = -Box2DUtils.height(shape) / 2;
        jointDef.localAnchorB.y = Box2DUtils.height(shape) / 2;

        final Chain.DefBuilder builder = new Chain.DefBuilder(world, bodyDef, fixtureDef, jointDef);
        final Chain chain = new Chain(10, builder);

        shape.dispose();

        return chain;
    }

    private void attachChainToChainHolder() {
        final Body segment = chain.getSegment(0);

        final RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.collideConnected = false;
        jointDef.bodyA = finish;
        jointDef.bodyB = segment;
        jointDef.localAnchorA.set(0.5f, 0);
        jointDef.localAnchorB.set(.1f / 2, 0.25f / 2);
        world.createJoint(jointDef);
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

        scoreLabel = new Label("", skin, "gold");
        scoreLabel.setAlignment(Align.left);
        timeLabel = new Label("", skin, "gold");
        timeLabel.setAlignment(Align.right);

        table.row().expand().top();
        table.columnDefaults(0).width(Value.percentWidth(0.25f, table));
        table.add(scoreLabel).left();
        table.add(timeLabel).right();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        particleEffect.update(delta);
        updateHUD();
        // Show only visible part of the Tiled Map on Y-axis - MathUtils.clamp()
//        cam.position.set(viewport.getWorldWidth() / 2, MathUtils.clamp(player.getPosition().y, cam.viewportHeight / 2, cam.viewportHeight * 2), 0);
        cam.update();

        b2dr.render(world, cam.combined);
        // TODO: 2016.10.03 uncomment when releasing in Prod
//        mapRenderer.setView(cam);
//        mapRenderer.render();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        // TODO: 2016.10.03 uncomment later
//        drawBodies();
//        drawParticles();
        game.batch.end();

        game.shapeRenderer.setAutoShapeType(true);
        game.shapeRenderer.begin();
        drawProjectile();
        game.shapeRenderer.end();

        stage.act();
        stage.draw();
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

    }

    private void drawBodies() {
        for (Body body : bodies) {
            if ((body.getUserData() != null) && (body.getUserData() instanceof String)) {
                final String userData = (String) body.getUserData();
                switch (userData) {
                    case "Block":
                        bodyRegion = starRegion;
                        break;
                    case "Circle":
                        bodyRegion = starRegion;
                        break;
                    case "Player":
                        bodyRegion = circleRainbowRegion;
                        break;
                    default:
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

    private void drawProjectile() {
        if (displacement != null && touchedPlayer) {
            game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            for (Vector2 point : displacement) {
                worldCoords.set(point.x, point.y, 0);
                cam.project(worldCoords);
                game.shapeRenderer.circle(worldCoords.x, worldCoords.y, 3.0f);
            }
        }
    }

    private void playCollisionSound() {
        collisionSound.play();
    }

    private QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(touchPos.x, touchPos.y)) {
                if (isPlayer(fixture.getBody())) {
                    touchedPlayer = true;
                } else {
                    touchedPlayer = false;
                }
            }
            return false;
        }
    };

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        mapRenderer.dispose();
        map.dispose();
        collisionSound.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
