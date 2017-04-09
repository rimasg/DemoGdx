package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.PhysicsComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class PhysicsSystem extends IteratingSystem {
    private static Family family = Family.all(PhysicsComponent.class, TransformComponent.class).get();

    public PhysicsSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PhysicsComponent physics = Mappers.physics.get(entity);
        final TransformComponent transform = Mappers.transform.get(entity);

        transform.pos.set(physics.body.getPosition());
        transform.rotation = physics.body.getAngle();
    }
}
