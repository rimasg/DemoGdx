package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.sid.demogdx.hunter.components.ExplosionComponent;
import com.sid.demogdx.hunter.components.ParticleComponent;
import com.sid.demogdx.hunter.components.StateComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.04.08.
 */

public class ExplosionSystem extends IteratingSystem {
    private static Family family = Family.all(ExplosionComponent.class, ParticleComponent.class, StateComponent.class).get();

    public static float delay = 1.0f;
    private Engine engine;

    public ExplosionSystem() {
        super(family);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final ParticleComponent particle = Mappers.particle.get(entity);
        final StateComponent state = Mappers.state.get(entity);

        if (state.time > delay) {
            engine.removeEntity(entity);
            if (particle.effect instanceof ParticleEffectPool.PooledEffect) {
                ((ParticleEffectPool.PooledEffect) particle.effect).free();
            } else {
                particle.effect.reset();
            }
            state.resetState();
        }
    }
}
