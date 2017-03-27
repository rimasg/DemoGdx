package com.sid.demogdx.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableBox2DObject;
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.hunter.EntityWorld;
import com.sid.demogdx.hunter.SeekAndAvoidSB;
import com.sid.demogdx.hunter.systems.PlayerSystem;
import com.sid.demogdx.hunter.systems.RenderingSystem;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.HunterCameraHelper;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import net.dermetfan.gdx.physics.box2d.ContactAdapter;

/**
 * Created by Okis on 2017.03.24.
 */

public class HunterAIScreen extends AbstractBox2dScreen {
    private final float unitScale = Box2DConfig.unitScale32;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DMapObjectParser box2DMapObjectParser;

    private SteerableBox2DObject steerable;
    private Ray<Vector2>[] steerableRays;

    private Body player;
    private Body spawn;
    private Body finish;

    PooledEngine engine;

    public HunterAIScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        HunterCameraHelper.setCam(cam);
        world.setGravity(Vector2.Zero);
        world.setContactListener(new ContactAdapter(){
            @Override
            public void beginContact(Contact contact) {
                super.beginContact(contact);
                final Body bodyA = contact.getFixtureA().getBody();
                final Body bodyB = contact.getFixtureB().getBody();
                if ((player == bodyA) && (finish == bodyB) || (finish == bodyA) && (player == bodyB)) {
                    steerable.setSteeringBehavior(null);
                }
            }
        });

        map = Assets.getTiledMap(AssetDescriptors.MAP_HUNTER);
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale, game.batch);
        box2DMapObjectParser = new Box2DMapObjectParser(mapRenderer.getUnitScale());
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
                    player = body;
                    HunterCameraHelper.setTarget(player);
                }
                if ("finish".equals(mapObject.getName())) {
                    finish = body;
                }
            }

        });

        createWorld();
        createSteerable();
        //
        engine = new PooledEngine();
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new RenderingSystem(game.batch, cam));
        new EntityWorld(engine, player).create();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        HunterCameraHelper.update(delta);
        b2dr.render(world, cam.combined);

        mapRenderer.setView(cam);
        mapRenderer.render();
        steerable.update(delta);

        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.circle(player.getPosition().x, player.getPosition().y, steerable
                .getBoundingRadius());
        for (Ray<Vector2> steerableRay : steerableRays) {
            game.shapeRenderer.line(steerableRay.start, steerableRay.end);
        }
        game.shapeRenderer.end();

        engine.update(delta);
    }

    @Override
    public void hide() {
        super.hide();
        mapRenderer.dispose();
        map.dispose();
        HunterCameraHelper.reset();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void createWorld() {
        box2DMapObjectParser.load(world, map);
    }

    private void createSteerable() {
        steerable = new SteerableBox2DObject(Assets.getRegion(RegionNames.HERO), player, 0.6f);

        final SteerableLocation location = new SteerableLocation();
        location.setPosition(finish.getPosition());

        final SeekAndAvoidSB seekAndAvoidSB = new SeekAndAvoidSB()
                .initSteering(world, steerable, location);
        final PrioritySteering<Vector2> steering = seekAndAvoidSB.getSteering();
        steerableRays = seekAndAvoidSB.getRays();

        steerable.setSteeringBehavior(steering);
    }
}
