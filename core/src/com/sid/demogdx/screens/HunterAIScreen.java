package com.sid.demogdx.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.systems.Box2DMapParserSystem;
import com.sid.demogdx.hunter.systems.PlayerSystem;
import com.sid.demogdx.hunter.systems.RenderingSystem;
import com.sid.demogdx.hunter.systems.ShapeRendererSystem;
import com.sid.demogdx.utils.HunterCameraHelper;

/**
 * Created by Okis on 2017.03.24.
 */

public class HunterAIScreen extends AbstractBox2dScreen {
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
        engine = new PooledEngine();
        entityWorld = new EntityWorld(game, world, engine);

        engine.addSystem(new Box2DMapParserSystem(cam));
        engine.addSystem(new RenderingSystem(game.batch, cam));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new ShapeRendererSystem(game.shapeRenderer, cam, entityWorld));

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        HunterCameraHelper.update(delta);
        b2dr.render(world, cam.combined);
        engine.update(delta);
    }

    @Override
    public void hide() {
        super.hide();
        engine.getSystem(Box2DMapParserSystem.class).dispose();
        HunterCameraHelper.reset();
    }
}
