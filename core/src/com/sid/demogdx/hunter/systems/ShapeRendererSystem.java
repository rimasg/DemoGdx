package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.03.26.
 */

public class ShapeRendererSystem extends IteratingSystem {
    private static Family family = Family.all(PlayerComponent.class).get();

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera cam;
    private EntityWorld entityWorld;

    public ShapeRendererSystem(ShapeRenderer shapeRenderer, OrthographicCamera cam, EntityWorld entityWorld) {
        super(family);
        this.shapeRenderer = shapeRenderer;
        this.cam = cam;
        this.entityWorld = entityWorld;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent player = Mappers.player.get(entity);

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(player.body.getPosition().x, player.body.getPosition().y, player.steerable.getBoundingRadius());
        final Ray<Vector2>[] rays = player.steerable.getSeekAndAvoidSB().getRays();
        for (Ray<Vector2> steerableRay : rays) {
            shapeRenderer.line(steerableRay.start, steerableRay.end);
        }
        shapeRenderer.end();
    }
}
