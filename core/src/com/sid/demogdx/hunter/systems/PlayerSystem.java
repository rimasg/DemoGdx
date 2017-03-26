package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TransformComponent;

/**
 * Created by Okis on 2017.03.26.
 */

public class PlayerSystem extends IteratingSystem {
    private static Family family = Family.all(PlayerComponent.class, TransformComponent.class).get();

    private ComponentMapper<PlayerComponent> plm;
    private ComponentMapper<TransformComponent> trm;

    public PlayerSystem() {
        super(family);

        plm = ComponentMapper.getFor(PlayerComponent.class);
        trm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent plc = plm.get(entity);
        final TransformComponent trc = trm.get(entity);
        trc.pos.set(plc.body.getPosition());
        trc.rotation = plc.body.getAngle();
    }
}
