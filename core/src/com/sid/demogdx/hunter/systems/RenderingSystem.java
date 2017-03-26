package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;

/**
 * Created by Okis on 2017.03.26.
 */

public class RenderingSystem extends IteratingSystem {
    private static Family family = Family.all(TextureComponent.class, TransformComponent.class).get();

    private ComponentMapper<TextureComponent> txm;
    private ComponentMapper<TransformComponent> trm;

    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Array<Entity> renderArray;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera cam) {
        super(family);
        this.batch = batch;
        this.cam = cam;

        txm = ComponentMapper.getFor(TextureComponent.class);
        trm = ComponentMapper.getFor(TransformComponent.class);

        renderArray = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Entity entity : renderArray) {
            final TextureComponent tx = txm.get(entity);
            if (tx.region == null) {
                continue;
            }
            final TransformComponent tr = trm.get(entity);

            final int w = 1;
//            final int w = tx.region.getRegionWidth();
            final int h = 1;
//            final int h = tx.region.getRegionHeight();
            final float ox = w / 2.f;
            final float oy = h / 2.f;
            batch.draw(tx.region, tr.pos.x - ox, tr.pos.y - oy, ox, oy, w, h, tr.scale.x, tr.scale.y, tr.rotation * MathUtils.radiansToDegrees);
        }
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderArray.add(entity);
    }
}
