package com.sid.demogdx.screens;

import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableBox2DObject;
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.hunter.SeekAndAvoidSB;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;
import com.sid.demogdx.utils.HunterCameraHelper;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import net.dermetfan.gdx.physics.box2d.ContactAdapter;

/**
 * Created by Okis on 2017.03.24.
 */

public class HunterAIScreen extends AbstractBox2dScreen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DMapObjectParser box2DMapObjectParser;

    private SteerableBox2DObject steerable;
    private Ray<Vector2>[] steerableRays;

    private Body player;
    private Body spawn;
    private Body finish;

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
        mapRenderer = new OrthogonalTiledMapRenderer(map, Box2DConfig.unitScale32, game.batch);
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
                final MapProperties props = mapObject.getProperties();
                float x = props.get(aliases.x, Float.class);
                float y = props.get(aliases.y, Float.class);

                x = Box2DConfig.pixelsToMeters(x, Box2DConfig.unitScale32);
                y = Box2DConfig.pixelsToMeters(y, Box2DConfig.unitScale32);

                if ("spawn".equals(mapObject.getName())) {
                    createPlayerBody(x, y);
                    HunterCameraHelper.setTarget(player);
                }
                if ("finish".equals(mapObject.getName())) {
                    createFinishBody(x, y);
                }
                return super.createObject(mapObject);
            }
        });

        createWorld();
        createSteerable();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        HunterCameraHelper.update(delta);
        b2dr.render(world, cam.combined);

        mapRenderer.setView(cam);
        mapRenderer.render();
        steerable.update(delta);

        game.batch.begin();
        steerable.draw(game.batch);
        game.batch.end();

        game.shapeRenderer.setProjectionMatrix(cam.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.circle(player.getPosition().x, player.getPosition().y, steerable
                .getBoundingRadius());
        for (Ray<Vector2> steerableRay : steerableRays) {
            game.shapeRenderer.line(steerableRay.start, steerableRay.end);
        }
        game.shapeRenderer.end();
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

    private void createPlayerBody(float x, float y) {
        player = Box2DUtils.createBox2dPolygonBody(world, x, y);
    }

    private void createFinishBody(float x, float y) {
        finish = Box2DUtils.createBox2dPolygonBody(world, x, y);
    }
}
