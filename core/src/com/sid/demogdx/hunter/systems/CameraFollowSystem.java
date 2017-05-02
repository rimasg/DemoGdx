package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.sid.demogdx.hunter.components.CameraFollowComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.entity.Mappers;

/**
 * Created by Okis on 2017.04.09.
 */

public class CameraFollowSystem extends IteratingSystem {
    private static Family family = Family.all(CameraFollowComponent.class).get();

    public static float VELOCITY = 4.0f;
    private static final Vector3 targetVec = new Vector3();

    public CameraFollowSystem() {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final CameraFollowComponent camera = Mappers.cameraFollow.get(entity);
        if (camera.cam == null) {
            return;
        }

        final TransformComponent transform = Mappers.transform.get(camera.target);
        if (transform == null) {
            return;
        }
        targetVec.set(camera.cam.viewportWidth / 2f, transform.pos.y, 0);
        camera.cam.position.lerp(targetVec, VELOCITY * deltaTime);
    }
}
