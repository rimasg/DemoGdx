package com.sid.demogdx.interfaces;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Okis on 2016.03.21 @ 21:07.
 */
public class Box2DMapObjectParserListenerAdapter extends Box2DMapObjectParser.Listener.Adapter {
    Box2DMapObjectParser box2DMapObjectParser;

    public Box2DMapObjectParserListenerAdapter(Box2DMapObjectParser box2DMapObjectParser) {
        this.box2DMapObjectParser = box2DMapObjectParser;
    }

    @Override
    public MapObject createObject(MapObject mapObject) {
        if (mapObject instanceof CircleMapObject || mapObject instanceof EllipseMapObject) {
            // get dimensions
            float width, height;
            if (mapObject instanceof CircleMapObject) {
                Circle circle = ((CircleMapObject) mapObject).getCircle();
                width = circle.radius * 2;
                height = circle.radius * 2;
            } else {
                Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse();
                width = ellipse.width;
                height = ellipse.height;
            }

            // adjust body position
            Box2DMapObjectParser.Aliases aliases = box2DMapObjectParser.getAliases();
            MapProperties props = mapObject.getProperties();
            props.put(aliases.x, props.get(aliases.x, Float.class) + width / 2);
            props.put(aliases.y, props.get(aliases.y, Float.class) + height / 2);
        }
        return super.createObject(mapObject);
    }
}
