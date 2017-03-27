package com.sid.demogdx.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.MapManager;
import com.sid.demogdx.hunter.systems.Box2DMapParserSystem;
import com.sid.demogdx.hunter.systems.PlayerSystem;
import com.sid.demogdx.hunter.systems.RenderingSystem;
import com.sid.demogdx.utils.HunterCameraHelper;

/**
 * Created by Okis on 2017.03.24.
 */

public class HunterAIScreen extends AbstractBox2dScreen implements MapManager.Box2DMapObjectParserCallback {
    private Body player;
    private Body spawn;
    private Body finish;

    private MapManager mapManager;
    private PooledEngine engine;
    private EntityWorld entityWorld;

    public HunterAIScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        HunterCameraHelper.setCam(cam);
        //
        mapManager = new MapManager(game, world, this);
        //
        engine = new PooledEngine();
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new RenderingSystem(game.batch, cam));
        //
        entityWorld = new EntityWorld(world, engine, player, finish);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        HunterCameraHelper.update(delta);
        b2dr.render(world, cam.combined);

        mapManager.mapRenderer.setView(cam);
        mapManager.mapRenderer.render();

        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.circle(player.getPosition().x, player.getPosition().y, entityWorld.getSteerable().getBoundingRadius());
        for (Ray<Vector2> steerableRay : entityWorld.getSteerableRays()) {
            game.shapeRenderer.line(steerableRay.start, steerableRay.end);
        }
        game.shapeRenderer.end();

        engine.update(delta);
    }

    @Override
    public void hide() {
        super.hide();
        mapManager.dispose();
        engine.getSystem(Box2DMapParserSystem.class).dispose();
        HunterCameraHelper.reset();
    }

    @Override
    public void setPlayer(Body body) {
        player = body;
    }

    @Override
    public void setFinish(Body body) {
        finish = body;
    }
}
