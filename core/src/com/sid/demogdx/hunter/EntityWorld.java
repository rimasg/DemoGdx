package com.sid.demogdx.hunter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.entities.SteerableBox2DObject;
import com.sid.demogdx.entities.SteerableLocation;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;

import net.dermetfan.gdx.physics.box2d.ContactAdapter;

/**
 * Created by Okis on 2017.03.26.
 */

public class EntityWorld {
    private World world;
    private PooledEngine engine;
    private Body player;
    private Body finish;
    private SteerableBox2DObject steerable;
    private Ray<Vector2>[] steerableRays;

    public EntityWorld(World world, PooledEngine engine, Body player, Body finish) {
        this.world = world;
        this.engine = engine;
        this.player = player;
        this.finish = finish;

        create();
    }

    private void create() {
        createWorld();
        createPlayer();
        createBox2DMapParser();
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

    private void createBox2DMapParser() {
        final Entity entity = engine.createEntity();

        final Box2DMapParserComponent parserc = engine.createComponent(Box2DMapParserComponent.class);

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
