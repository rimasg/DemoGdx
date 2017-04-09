package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Disposable;
import com.sid.demogdx.hunter.components.Box2DMapRendererComponent;
import com.sid.demogdx.hunter.components.CameraComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by SID on 2017-03-27.
 */

public class Box2DMapRendererSystem extends IteratingSystem implements Disposable {
    private static Family family = Family.all(Box2DMapRendererComponent.class, CameraComponent.class).get();
    private Box2DMapRendererComponent renderer;

    public Box2DMapRendererSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderer = Mappers.box2DMapParser.get(entity);
        final CameraComponent camera = Mappers.camera.get(entity);

        renderer.mapRenderer.setView(camera.cam);
        renderer.mapRenderer.render();
    }

    @Override
    public void dispose() {
        renderer.mapRenderer.dispose();
    }

}
