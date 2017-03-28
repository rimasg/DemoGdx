package com.sid.demogdx.hunter;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.hunter.systems.Box2DMapParserSystem;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.HunterCameraHelper;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by SID on 2017-03-27.
 */

public class MapManager implements Disposable{
    private TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;

    private DemoGdx game;
    private World world;
    private Box2DMapParserSystem.Box2DMapParserCallback callback;

    public MapManager(DemoGdx game, World world, Box2DMapParserSystem.Box2DMapParserCallback callback) {
        this.game = game;
        this.world = world;
        this.callback = callback;

        parseBox2DMap();
    }

    private void parseBox2DMap() {
        map = Assets.getTiledMap(AssetDescriptors.MAP_HUNTER);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Box2DConfig.unitScale32, game.batch);
        Box2DMapObjectParser box2DMapObjectParser = new Box2DMapObjectParser(mapRenderer.getUnitScale());
        box2DMapObjectParser.setListener(new Box2DMapObjectParser.Listener.Adapter(){

            Box2DMapObjectParser.Aliases aliases;

            @Override
            public void init(Box2DMapObjectParser parser) {
                super.init(parser);
                aliases = parser.getAliases();
            }

            @Override
            public MapObject createObject(MapObject mapObject) {
                if (mapObject instanceof RectangleMapObject) {
                    final Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
                    float halfWidth = rect.width / 2;
                    float halfHeight = rect.height / 2;

                    final MapProperties props = mapObject.getProperties();
                    props.put(aliases.x, props.get(aliases.x, Float.class) + halfWidth);
                    props.put(aliases.y, props.get(aliases.y, Float.class) + halfHeight);
                }
                return super.createObject(mapObject);
            }

            @Override
            public void created(Body body, MapObject mapObject) {
                super.created(body, mapObject);
                if ("spawn".equals(mapObject.getName())) {
                    callback.setPlayer(body);
                    HunterCameraHelper.setTarget(body);
                }
                if ("finish".equals(mapObject.getName())) {
                    callback.setFinish(body);
                }
            }

        });
        box2DMapObjectParser.load(world, map);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }
}
