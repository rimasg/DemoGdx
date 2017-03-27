package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Disposable;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;

/**
 * Created by SID on 2017-03-27.
 */

public class Box2DMapParserSystem extends IteratingSystem implements Disposable {
    private static Family family = Family.all(Box2DMapParserComponent.class).get();

    public Box2DMapParserSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void dispose() {

    }
}
