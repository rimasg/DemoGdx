package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sid.demogdx.hunter.components.AnimationComponent;
import com.sid.demogdx.hunter.components.StateComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.utils.entity.Mappers;

/**
 * Created by SID on 2017-04-13.
 */

public class AnimationSystem extends IteratingSystem {
    private static Family family = Family.all(AnimationComponent.class, TextureComponent.class, StateComponent.class).get();

    public AnimationSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent anim = Mappers.anim.get(entity);
        final TextureComponent texture = Mappers.texture.get(entity);
        final StateComponent state = Mappers.state.get(entity);

        final Animation<TextureRegion> animation = anim.anim.get(state.state);

        if (animation != null) {
            texture.region = animation.getKeyFrame(state.time);
        }
    }
}
