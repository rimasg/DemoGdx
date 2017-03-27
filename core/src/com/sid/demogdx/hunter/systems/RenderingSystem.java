package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class RenderingSystem extends IteratingSystem {
    private static Family family = Family.all(TextureComponent.class, TransformComponent.class).get();

    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Array<Entity> renderArray;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(family);
        this.batch = batch;
        this.cam = cam;

        renderArray = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Entity entity : renderArray) {
            final TextureComponent texture = Mappers.texture.get(entity);
            if (texture.region == null) {
                continue;
            }
            final TransformComponent transform = Mappers.transform.get(entity);

            final int w = 1;
//            final int w = texture.region.getRegionWidth();
            final int h = 1;
//            final int h = texture.region.getRegionHeight();
            final float ox = w / 2.f;
            final float oy = h / 2.f;
            batch.draw(texture.region, transform.pos.x - ox, transform.pos.y - oy, ox, oy, w, h, transform.scale.x, transform.scale.y, transform.rotation * MathUtils.radiansToDegrees);
        }
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderArray.add(entity);
    }
}