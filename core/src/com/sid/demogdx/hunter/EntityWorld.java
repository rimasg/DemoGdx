package com.sid.demogdx.hunter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableBox2DObject;
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;
import com.sid.demogdx.hunter.components.ObstacleComponent;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.hunter.systems.Box2DMapParserSystem;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.HunterCameraHelper;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import net.dermetfan.gdx.physics.box2d.ContactAdapter;

/**
 * Created by Okis on 2017.03.26.
 */

public class EntityWorld {
    private DemoGdx game;
    private World world;
    private PooledEngine engine;
    private Box2DMapParserSystem.Box2DMapParserCallback parserCallback;
    private Body player;
    private Body finish;

    private SteerableBox2DObject steerable;
    private Ray<Vector2>[] steerableRays;

    public EntityWorld(DemoGdx game, World world, PooledEngine engine, Box2DMapParserSystem.Box2DMapParserCallback parserCallback) {
        this.game = game;
        this.world = world;
        this.engine = engine;
        this.parserCallback = parserCallback;

        create();
    }

    private void create() {
        createWorld();
        createBox2DMapParser();
        createPlayer();
    }

    private void createWorld() {
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
    }

    private void createPlayer() {
        final Entity entity = engine.createEntity();

        final PlayerComponent plc = engine.createComponent(PlayerComponent.class);
        final TextureComponent txc = engine.createComponent(TextureComponent.class);
        final TransformComponent trc = engine.createComponent(TransformComponent.class);

        plc.body = player;
        plc.steerable = createSteerable();
        txc.region = Assets.getRegion(RegionNames.HERO);

        entity.add(plc);
        entity.add(txc);
        entity.add(trc);

        engine.addEntity(entity);
    }

    private void createObstacle(Body body) {
        final Entity entity = engine.createEntity();

        final ObstacleComponent obc = engine.createComponent(ObstacleComponent.class);
        final TextureComponent txc = engine.createComponent(TextureComponent.class);
        final TransformComponent trc = engine.createComponent(TransformComponent.class);

        obc.body = body;
        txc.region = Assets.getRegion(RegionNames.STAR);

        entity.add(obc);
        entity.add(txc);
        entity.add(trc);

        engine.addEntity(entity);
    }

    private void createBox2DMapParser() {
        final Entity entity = engine.createEntity();

        final Box2DMapParserComponent parserc = engine.createComponent(Box2DMapParserComponent.class);
        parserc.map = Assets.getTiledMap(AssetDescriptors.MAP_HUNTER);
        parserc.mapRenderer = new OrthogonalTiledMapRenderer(parserc.map, Box2DConfig.unitScale32, game.batch);
        parserc.box2DMapObjectParser = new Box2DMapObjectParser(parserc.mapRenderer.getUnitScale());
        parserc.box2DMapObjectParser.setListener(new Box2DMapObjectParser.Listener.Adapter() {

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
                    parserCallback.setPlayer(player);
                    HunterCameraHelper.setTarget(body);
                }
                if ("finish".equals(mapObject.getName())) {
                    finish = body;
                    parserCallback.setFinish(finish);
                }
                if ("obstacle".equals(mapObject.getName())) {
                    createObstacle(body);
                }
            }
        });
        parserc.box2DMapObjectParser.load(world, parserc.map);

        entity.add(parserc);

        engine.addEntity(entity);
    }

    private SteerableBox2DObject createSteerable() {
        steerable = new SteerableBox2DObject(Assets.getRegion(RegionNames.HERO), player, 0.6f);

        final SteerableLocation location = new SteerableLocation();
        location.setPosition(finish.getPosition());

        final SeekAndAvoidSB seekAndAvoidSB = new SeekAndAvoidSB()
                .initSteering(world, steerable, location);
        final PrioritySteering<Vector2> steering = seekAndAvoidSB.getSteering();
        steerableRays = seekAndAvoidSB.getRays();

        steerable.setSteeringBehavior(steering);

        return steerable;
    }

    public SteerableBox2DObject getSteerable() {
        return steerable;
    }

    public Ray<Vector2>[] getSteerableRays() {
        return steerableRays;
    }
}
