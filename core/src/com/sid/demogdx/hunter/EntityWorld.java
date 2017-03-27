package com.sid.demogdx.hunter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;

/**
 * Created by Okis on 2017.03.26.
 */

public class EntityWorld {
    private PooledEngine engine;
    private Body player;

    public EntityWorld(PooledEngine engine, Body player) {
        this.engine = engine;
        this.player = player;
    }

    public void create() {
        createPlayer();
    }

    private void createPlayer() {
        final Entity playerEntity = engine.createEntity();

        final PlayerComponent plc = engine.createComponent(PlayerComponent.class);
        final TextureComponent txc = engine.createComponent(TextureComponent.class);
        final TransformComponent trc = engine.createComponent(TransformComponent.class);

        plc.body = player;
        txc.region = Assets.getRegion(RegionNames.HERO);

        playerEntity.add(plc);
        playerEntity.add(txc);
        playerEntity.add(trc);

        engine.addEntity(playerEntity);
    }
}
