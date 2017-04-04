package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.ObstacleComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class ObstacleSystem extends IteratingSystem {
    private static Family family = Family.all(ObstacleComponent.class, TransformComponent.class).get();

    public ObstacleSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final ObstacleComponent obstacle = Mappers.obstacle.get(entity);
        final TransformComponent transform = Mappers.transform.get(entity);
        transform.pos.set(obstacle.body.getPosition());
        transform.rotation = obstacle.body.getAngle();
    }
}
