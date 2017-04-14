package com.sid.demogdx.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.GdxAI;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.systems.AnimationSystem;
import com.sid.demogdx.hunter.systems.BoundsSystem;
import com.sid.demogdx.hunter.systems.Box2DMapRendererSystem;
import com.sid.demogdx.hunter.systems.CameraFollowSystem;
import com.sid.demogdx.hunter.systems.ExplosionSystem;
import com.sid.demogdx.hunter.systems.PhysicsSystem;
import com.sid.demogdx.hunter.systems.PlayerRendererSystem;
import com.sid.demogdx.hunter.systems.PlayerSystem;
import com.sid.demogdx.hunter.systems.RenderingSystem;
import com.sid.demogdx.hunter.systems.StateSystem;
import com.sid.demogdx.hunter.systems.TiledPathRenderingSystem;

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

        engine = new PooledEngine();
        entityWorld = new EntityWorld(game, world, engine);

        engine.addSystem(new PlayerSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new CameraFollowSystem());
        engine.addSystem(new Box2DMapRendererSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new ExplosionSystem());
        engine.addSystem(new RenderingSystem(game.batch, cam));
        engine.addSystem(new PlayerRendererSystem(game.shapeRenderer, cam));
        engine.addSystem(new TiledPathRenderingSystem(game.shapeRenderer, cam));

        entityWorld.create();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        GdxAI.getTimepiece().update(delta);
        engine.update(delta);
    }

    @Override
    public void hide() {
        super.hide();
        engine.getSystem(Box2DMapRendererSystem.class).dispose();
        engine.removeAllEntities();
    }
}
