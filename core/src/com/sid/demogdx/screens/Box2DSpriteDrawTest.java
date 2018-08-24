package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.utils.Box2DConfig;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2016.03.19 @ 10:05.
 */
public class Box2DSpriteDrawTest extends AbstractBox2dScreen {
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Box2DMapObjectParser box2DMapObjectParser;

    private Body player;
    private TextureAtlas.AtlasRegion bodyRegion;

    public Box2DSpriteDrawTest(DemoGdx game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // Show only visible part of the Tiled Map on Y-axis - MathUtils.clamp()
        if (player != null) {
            cam.position.set(viewport.getWorldWidth() / 2, MathUtils.clamp(player.getPosition().y, cam.viewportHeight / 2, cam.viewportHeight * 2), 0);
        }
        cam.update();

        b2dr.render(world, cam.combined);
        mapRenderer.setView(cam);
        mapRenderer.render();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        // TODO: 2018.08.23 add something to draw
        Box2DSprite.draw(game.batch, world);
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    protected void loadAssets() {
        bodyRegion = Assets.inst().getRegion(RegionNames.STAR);
    }

    @Override
    protected void init() {
        super.init();

        map = new TmxMapLoader().load("maps/box2d_test.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, Box2DConfig.unitScale32, game.batch);
        box2DMapObjectParser = new Box2DMapObjectParser(mapRenderer.getUnitScale());
//        box2DMapObjectParser.setListener(new Box2DMapObjectParserListenerAdapter(box2DMapObjectParser));

        createWorld();
        player = createPlayer();
        player.setUserData(new Box2DSprite(bodyRegion));
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
        world.getBodies(bodies);
    }

    private Body createPlayer() {
        for (Body body : bodies) {
            if (isPlayer(body)) {
                return body;
            }
        }
        return null;
    }

    private boolean isPlayer(Body body) {
        return body.getUserData() != null && body.getUserData().equals("player");
    }

    private boolean isFinish(Body body) {
        return body.getUserData() != null && body.getUserData().equals("finish");
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

        awakePlayer();
    }

    private void moveBallOnXAxis(float x) {
        player.applyForceToCenter(x, 0, true);
    }

    private void awakePlayer() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.setAwake(true);
        }
    }

    @Override
    public void hide() {
        super.hide();
        mapRenderer.dispose();
        map.dispose();
    }
}
