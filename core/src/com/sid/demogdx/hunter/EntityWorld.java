package com.sid.demogdx.hunter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.hunter.components.AnimationComponent;
import com.sid.demogdx.hunter.components.Box2DMapRendererComponent;
import com.sid.demogdx.hunter.components.CameraComponent;
import com.sid.demogdx.hunter.components.CameraFollowComponent;
import com.sid.demogdx.hunter.components.EnemyComponent;
import com.sid.demogdx.hunter.components.ExplosionComponent;
import com.sid.demogdx.hunter.components.ObstacleComponent;
import com.sid.demogdx.hunter.components.ParticleComponent;
import com.sid.demogdx.hunter.components.PhysicsComponent;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.StateComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TiledPathFinderComponent;
import com.sid.demogdx.hunter.components.TransformComponent;
import com.sid.demogdx.hunter.fsm.PlayerAgent;
import com.sid.demogdx.hunter.pfa.TiledGraph;
import com.sid.demogdx.hunter.pfa.TiledNode;
import com.sid.demogdx.hunter.pfa.TiledPathFinder;
import com.sid.demogdx.hunter.systems.RenderingSystem;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Mappers;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import net.dermetfan.gdx.physics.box2d.ContactAdapter;

/**
 * Created by Okis on 2017.03.26.
 */

public class EntityWorld {
    private static final String TAG = "EntityWorld";
    private DemoGdx game;
    private World world;
    private PooledEngine engine;
    private Body player;
    private Body finish;
    private ParticleEffectPool particleExplosionEffectPool;

    public EntityWorld(DemoGdx game, World world, PooledEngine engine) {
        this.game = game;
        this.world = world;
        this.engine = engine;
    }

    public void create() {
        particleExplosionEffectPool = new ParticleEffectPool(Assets.inst().getParticleEffect(AssetDescriptors.PE_EXPLOSION_BOX2D), 5, 20);
        createWorld();
        createBox2DMapParser();
        createFollowCamera(createPlayer());
        createAStarMap();
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

                final Object userDataA = bodyA.getUserData();
                final Object userDataB = bodyB.getUserData();
                Entity entityA = null;
                if (userDataA instanceof Entity) {
                    entityA = (Entity) userDataA;
                }
                final EnemyComponent enemyA;
                if (entityA != null) {
                    enemyA = Mappers.enemy.get(entityA);
                    if (enemyA != null) {
                        final PhysicsComponent physics = Mappers.physics.get(entityA);
                        createExplosion(physics.body);
                    }
                }
                //
                Entity entityB = null;
                if (userDataA instanceof Entity) {
                    entityB = (Entity) userDataB;
                }
                final EnemyComponent enemyB;
                if (entityB != null) {
                    enemyB = Mappers.enemy.get(entityB);
                    if (enemyB != null) {
                        final PhysicsComponent physics = Mappers.physics.get(entityB);
                        createExplosion(physics.body);
                    }
                }

            }
        });
    }

    private void createBox2DMapParser() {
        final Entity entity = engine.createEntity();

        final Box2DMapRendererComponent box2DMapRenderer = engine.createComponent(Box2DMapRendererComponent.class);
        final CameraComponent camera = engine.createComponent(CameraComponent.class);

        box2DMapRenderer.map = Assets.inst().getTiledMap(AssetDescriptors.MAP_HUNTER);
        box2DMapRenderer.mapRenderer = new OrthogonalTiledMapRenderer(box2DMapRenderer.map, Box2DConfig.unitScale32, game.batch);
        box2DMapRenderer.box2DMapObjectParser = new Box2DMapObjectParser(box2DMapRenderer.mapRenderer.getUnitScale());
        box2DMapRenderer.box2DMapObjectParser.setListener(new Box2DMapObjectParser.Listener.Adapter() {

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
                }
                if ("finish".equals(mapObject.getName())) {
                    finish = body;
                }
                if ("enemy".equals(mapObject.getName())) {
                    createEnemy(body);
                }
                if ("obstacle".equals(mapObject.getName())) {
                    createObstacle(body);
                }
                if ("wall".equals(mapObject.getName())) {
                    createWall(body);
                }
            }
        });
        box2DMapRenderer.box2DMapObjectParser.load(world, box2DMapRenderer.map);
        camera.cam = engine.getSystem(RenderingSystem.class).getCamera();

        entity.add(box2DMapRenderer);
        entity.add(camera);

        engine.addEntity(entity);
    }

    private void createFollowCamera(Entity player) {
        final Entity entity = engine.createEntity();

        final CameraFollowComponent camera = engine.createComponent(CameraFollowComponent.class);
        camera.cam = engine.getSystem(RenderingSystem.class).getCamera();
        camera.target = player;

        entity.add(camera);

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

    private void createAStarMap() {
        final Entity entity = engine.createEntity();

        final TiledPathFinderComponent pathFinder = engine.createComponent(TiledPathFinderComponent.class);

        final TiledMap tiledMap = Assets.inst().getTiledMap(AssetDescriptors.MAP_HUNTER);
        final MapProperties mapProperties = tiledMap.getProperties();
        final int width = (int) mapProperties.get("width");
        final int height = (int) mapProperties.get("height");
        pathFinder.pathFinder = new TiledPathFinder(width, height);
        //
        final TiledGraph graph = pathFinder.pathFinder.getGraph();
        //
        final MapObjects obstacles = tiledMap.getLayers().get("obstacles").getObjects();
        for (MapObject obstacle : obstacles) {
            if (obstacle instanceof RectangleMapObject) {
                RectangleMapObject object = (RectangleMapObject) obstacle;
                final int x = (int) (object.getRectangle().x / 32f);
                final int y = (int) (object.getRectangle().y / 32f);
                graph.getNode(x, y).type = TiledNode.Tile.OBSTACLE;
            }
        }
        //
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final TiledNode node = graph.getNode(x, y);
                if (x > 0) graph.addConnection(node, -1, 0);
                if (y > 0) graph.addConnection(node, 0, -1);
                if (x < width - 1) graph.addConnection(node, 1, 0);
                if (y < height - 1) graph.addConnection(node, 0, 1);
            }
        }
        //
        final TiledNode startNode = graph.getNode((int) player.getPosition().x, (int) player.getPosition().y);
        final TiledNode targetNode = graph.getNode((int) finish.getPosition().x, (int) finish.getPosition().y);
        pathFinder.pathFinder.findPath(startNode, targetNode);
        //
        entity.add(pathFinder);

        engine.addEntity(entity);
    }

    private Entity createPlayer() {
        final Entity entity = engine.createEntity();

        final PlayerComponent player = engine.createComponent(PlayerComponent.class);
        final TransformComponent transform = engine.createComponent(TransformComponent.class);
        final StateComponent state = engine.createComponent(StateComponent.class);
        final TextureComponent texture = engine.createComponent(TextureComponent.class);
        final ParticleComponent particle = engine.createComponent(ParticleComponent.class);
        final AnimationComponent anim = engine.createComponent(AnimationComponent.class);

        player.body = this.player;
        player.steerable = createSteerable();
        player.playerAgent = new PlayerAgent(player);
        state.state = 0;
        texture.region = Assets.inst().getRegion(RegionNames.HERO);
        particle.effect = Assets.inst().getParticleEffect(AssetDescriptors.PE_DEFAULT_BOX2D);

        // TODO: 2017-04-13 Create factory for Animations
        final TextureAtlas.AtlasRegion region = Assets.inst().getAtlas().findRegion(RegionNames.ANIM_SLIME);
        final TextureRegion[][] regions = region.split(32, 32);
        final Array<TextureRegion> keyframes = new Array<>();
        for (TextureRegion[] textureRegions : regions) {
            for (TextureRegion textureRegion : textureRegions) {
                keyframes.add(textureRegion);
            }
        }

        anim.anim.put(state.state, new Animation<>(0.2f, keyframes, Animation.PlayMode.LOOP));

        entity.add(player);
        entity.add(transform);
        entity.add(state);
        entity.add(texture);
        // TODO: 2017-04-14 uncomment later
//        entity.add(particle);
        entity.add(anim);

        engine.addEntity(entity);

        return entity;
    }

    private void createObstacle(Body body) {
        final Entity entity = engine.createEntity();

        final ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        final TransformComponent transform = engine.createComponent(TransformComponent.class);
        final PhysicsComponent physics = engine.createComponent(PhysicsComponent.class);
        final TextureComponent texture = engine.createComponent(TextureComponent.class);

        physics.body = body;
        texture.region = Assets.inst().getRegion(RegionNames.STAR);

        entity.add(obstacle);
        entity.add(transform);
        entity.add(physics);
        entity.add(texture);

        engine.addEntity(entity);
    }

    private void createEnemy(Body body) {
        final Entity entity = engine.createEntity();

        final EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
        final PhysicsComponent physics = engine.createComponent(PhysicsComponent.class);

        physics.body = body;
        physics.body.setUserData(entity);

        entity.add(enemy);
        entity.add(physics);

        engine.addEntity(entity);
    }

    private void createExplosion(Body body) {
        final Entity entity = engine.createEntity();

        final ExplosionComponent explosion = engine.createComponent(ExplosionComponent.class);
        final ParticleComponent particle = engine.createComponent(ParticleComponent.class);
        final TransformComponent transform = engine.createComponent(TransformComponent.class);
        final StateComponent state = engine.createComponent(StateComponent.class);

        particle.effect = particleExplosionEffectPool.obtain();
        transform.pos.set(body.getPosition());

        entity.add(explosion);
        entity.add(particle);
        entity.add(transform);
        entity.add(state);

        engine.addEntity(entity);
    }

    private void createWall(Body body) {
        // TODO: 2017.04.14 impl
    }
}
