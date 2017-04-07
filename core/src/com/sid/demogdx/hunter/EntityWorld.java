package com.sid.demogdx.hunter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
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
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;
import com.sid.demogdx.hunter.components.ObstacleComponent;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.hunter.fsm.PlayerAgent;
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
    private Body player;
    private Body finish;

    public EntityWorld(DemoGdx game, World world, PooledEngine engine) {
        this.game = game;
        this.world = world;
        this.engine = engine;

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
                    // no-op
                }
            }
        });
    }

    private void createPlayer() {
        final Entity entity = engine.createEntity();

        final PlayerComponent player = engine.createComponent(PlayerComponent.class);
        final TextureComponent texture = engine.createComponent(TextureComponent.class);
        final TransformComponent transform = engine.createComponent(TransformComponent.class);

        player.body = this.player;
        player.steerable = createSteerable();
        player.playerAgent = new PlayerAgent(player);
        texture.region = Assets.inst().getRegion(RegionNames.HERO);

        entity.add(player);
        entity.add(texture);
        entity.add(transform);

        engine.addEntity(entity);
    }

    private void createObstacle(Body body) {
        final Entity entity = engine.createEntity();

        final ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        final TextureComponent texture = engine.createComponent(TextureComponent.class);
        final TransformComponent transform = engine.createComponent(TransformComponent.class);

        obstacle.body = body;
        texture.region = Assets.inst().getRegion(RegionNames.STAR);

        entity.add(obstacle);
        entity.add(texture);
        entity.add(transform);

        engine.addEntity(entity);
    }

    private void createBox2DMapParser() {
        final Entity entity = engine.createEntity();

        final Box2DMapParserComponent box2DMapParser = engine.createComponent(Box2DMapParserComponent.class);
        box2DMapParser.map = Assets.inst().getTiledMap(AssetDescriptors.MAP_HUNTER);
        box2DMapParser.mapRenderer = new OrthogonalTiledMapRenderer(box2DMapParser.map, Box2DConfig.unitScale32, game.batch);
        box2DMapParser.box2DMapObjectParser = new Box2DMapObjectParser(box2DMapParser.mapRenderer.getUnitScale());
        box2DMapParser.box2DMapObjectParser.setListener(new Box2DMapObjectParser.Listener.Adapter() {

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
                    HunterCameraHelper.setTarget(body);
                }
                if ("finish".equals(mapObject.getName())) {
                    finish = body;
                }
                if ("obstacle".equals(mapObject.getName())) {
                    createObstacle(body);
                }
            }
        });
        box2DMapParser.box2DMapObjectParser.load(world, box2DMapParser.map);

        entity.add(box2DMapParser);

        engine.addEntity(entity);
    }

    private HunterSteerableObject createSteerable() {
        HunterSteerableObject steerable = new HunterSteerableObject(Assets.inst().getRegion(RegionNames.HERO), player, 0.6f);

        final SteerableLocation startLocation = new SteerableLocation();
        startLocation.setPosition(player.getPosition());

        final SteerableLocation targetLocation = new SteerableLocation();
        targetLocation.setPosition(finish.getPosition());

        final SeekAndAvoidSB seekAndAvoidSB = new SeekAndAvoidSB()
                .initSteering(world, steerable, startLocation, targetLocation);
        steerable.setSeekAndAvoidSB(seekAndAvoidSB);
        final PrioritySteering<Vector2> steering = seekAndAvoidSB.getSteering();

        steerable.setSteeringBehavior(steering);

        return steerable;
    }
}
