package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.hunter.components.ParticleComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class RenderingSystem extends IteratingSystem {
    private static Family family = Family
            .one(TextureComponent.class, ParticleComponent.class)
            .all(TransformComponent.class).get();

    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Array<Entity> renderQueue;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(family);
        this.batch = batch;
        this.cam = cam;

        renderQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Entity entity : renderQueue) {
            final TransformComponent transform = Mappers.transform.get(entity);
            final TextureComponent texture = Mappers.texture.get(entity);
            final ParticleComponent particle = Mappers.particle.get(entity);

            if (texture != null && texture.region != null) {
                final int w = 1;
                final int h = 1;
                final float ox = w / 2.f;
                final float oy = h / 2.f;
                batch.draw(texture.region, transform.pos.x - ox, transform.pos.y - oy, ox, oy, w, h, transform.scale.x, transform.scale.y, transform.rotation * MathUtils.radiansToDegrees);
            }

            if (particle != null && particle.effect != null) {
                if (!particle.effect.isComplete()) {
                    particle.effect.setPosition(transform.pos.x, transform.pos.y);
                    particle.effect.draw(batch, deltaTime);
                }
            }
        }
        batch.end();
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
