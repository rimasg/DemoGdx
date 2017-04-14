package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.ExplosionComponent;
import com.sid.demogdx.hunter.components.StateComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.04.08.
 */

public class ExplosionSystem extends IteratingSystem {
    private static Family family = Family.all(ExplosionComponent.class, StateComponent.class).get();

    private static float delay = 0.75f;
    private Engine engine;

    public ExplosionSystem() {
        super(family);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;
        super.addedToEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final StateComponent state = Mappers.state.get(entity);

        if (state.time > delay) {
            engine.removeEntity(entity);
        }
    }
}
