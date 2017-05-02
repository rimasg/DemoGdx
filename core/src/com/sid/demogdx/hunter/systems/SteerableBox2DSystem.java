package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.SteerableComponent;
import com.sid.demogdx.utils.entity.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class SteerableBox2DSystem extends IteratingSystem {
    private static Family family = Family.all(SteerableComponent.class).get();

    public SteerableBox2DSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final SteerableComponent steerable = Mappers.steerable.get(entity);

        steerable.steerable.update(deltaTime);
    }
}
