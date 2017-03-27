package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by SID on 2017-03-27.
 */

public class Box2DMapParserComponent implements Component {
    public TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;
    public Box2DMapObjectParser box2DMapObjectParser;
    // TODO: 2017-03-27 implement this and ParserSystem
}
