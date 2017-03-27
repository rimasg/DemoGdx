package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class PlayerSystem extends IteratingSystem {
    private static Family family = Family.all(PlayerComponent.class, TransformComponent.class).get();

    public PlayerSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent player = Mappers.player.get(entity);
        final TransformComponent transform = Mappers.transform.get(entity);
        transform.pos.set(player.body.getPosition());
        transform.rotation = player.body.getAngle();
    }
}
