package com.sid.demogdx.hunter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;
import com.sid.demogdx.utils.Mappers;

/**
 * Created by SID on 2017-03-27.
 */

public class Box2DMapParserSystem extends IteratingSystem implements Disposable {
    private static Family family = Family.all(Box2DMapParserComponent.class).get();
    private Box2DMapParserComponent parser;
    private OrthographicCamera cam;

    public Box2DMapParserSystem(OrthographicCamera cam) {
        super(family);
        this.cam = cam;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        parser = Mappers.box2DMapParser.get(entity);
        parser.mapRenderer.setView(cam);
        parser.mapRenderer.render();
    }

    @Override
    public void dispose() {
        parser.mapRenderer.dispose();
    }

}
