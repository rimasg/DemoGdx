package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.components.TiledPathFinderComponent;
import com.sid.demogdx.hunter.pfa.TiledNode;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by Okis on 2017.04.08.
 */

public class TiledPathFinderRenderingSystem extends IteratingSystem {
    private static Family family = Family.all(TiledPathFinderComponent.class).get();

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera cam;
    private EntityWorld entityWorld;

    public TiledPathFinderRenderingSystem(ShapeRenderer shapeRenderer, OrthographicCamera cam, EntityWorld entityWorld) {
        super(family);
        this.shapeRenderer = shapeRenderer;
        this.cam = cam;
        this.entityWorld = entityWorld;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final TiledPathFinderComponent pathFinder = Mappers.tiledPath.get(entity);

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        final int nodeCount = pathFinder.pathFinder.getPath().getCount();
        float hw = 1f / 2f;
        if (nodeCount > 0) {
            TiledNode prevNode = pathFinder.pathFinder.getPath().nodes.get(0);
            for (int i = 1; i < nodeCount; i++) {
                final TiledNode node = pathFinder.pathFinder.getPath().nodes.get(i);
                shapeRenderer.line(prevNode.x + hw, prevNode.y + hw, node.x + hw, node.y + hw);
                prevNode = node;
            }
        }
        shapeRenderer.end();
    }
}
