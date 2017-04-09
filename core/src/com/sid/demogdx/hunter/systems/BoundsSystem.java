package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.BoundsComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.04.08.
 */

public class BoundsSystem extends IteratingSystem {
    private static Family family = Family.all(BoundsComponent.class, TransformComponent.class).get();

    public BoundsSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final BoundsComponent bounds = Mappers.bounds.get(entity);
        final TransformComponent transform = Mappers.transform.get(entity);

        bounds.bounds.x = transform.pos.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = transform.pos.y - bounds.bounds.height * 0.5f;
    }
}
