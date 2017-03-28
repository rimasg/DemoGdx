package com.sid.demogdx.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.systems.Box2DMapParserSystem;
import com.sid.demogdx.hunter.systems.PlayerSystem;
import com.sid.demogdx.hunter.systems.RenderingSystem;
import com.sid.demogdx.utils.HunterCameraHelper;

/**
 * Created by Okis on 2017.03.24.
 */

public class HunterAIScreen extends AbstractBox2dScreen {
    private PooledEngine engine;

    public HunterAIScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        HunterCameraHelper.setCam(cam);
        //
        engine = new PooledEngine();
        engine.addSystem(new Box2DMapParserSystem(cam));
        engine.addSystem(new RenderingSystem(game.batch, cam));
        engine.addSystem(new PlayerSystem());
        //
        new EntityWorld(game, world, engine);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        HunterCameraHelper.update(delta);
        b2dr.render(world, cam.combined);
/*
        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.circle(player.getPosition().x, player.getPosition().y, entityWorld.getSteerable().getBoundingRadius());
        for (Ray<Vector2> steerableRay : entityWorld.getSteerableRays()) {
            game.shapeRenderer.line(steerableRay.start, steerableRay.end);
        }
        game.shapeRenderer.end();
*/

        engine.update(delta);
    }

    @Override
    public void hide() {
        super.hide();
        engine.getSystem(Box2DMapParserSystem.class).dispose();
        HunterCameraHelper.reset();
    }
}
