package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sid.demogdx.hunter.components.StateComponent;
import com.sid.demogdx.utils.entity.Mappers;

/**
 * Created by SID on 2017-04-13.
 */

public class StateSystem extends IteratingSystem {
    private static Family family = Family.all(StateComponent.class).get();

    public StateSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final StateComponent state = Mappers.state.get(entity);

        state.time += deltaTime;
    }
}
