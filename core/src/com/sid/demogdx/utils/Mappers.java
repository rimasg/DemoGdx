package com.sid.demogdx.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.sid.demogdx.hunter.components.BoundsComponent;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;
import com.sid.demogdx.hunter.components.ParticleComponent;
import com.sid.demogdx.hunter.components.PhysicsComponent;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TiledPathFinderComponent;
import com.sid.demogdx.hunter.components.TransformComponent;

/**
 * Created by SID on 2017-03-27.
 */

public final class Mappers {
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<ParticleComponent> particle = ComponentMapper.getFor(ParticleComponent.class);
    public static final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<TiledPathFinderComponent> tiledPath = ComponentMapper.getFor(TiledPathFinderComponent.class);
    public static final ComponentMapper<Box2DMapParserComponent> box2DMapParser = ComponentMapper.getFor(Box2DMapParserComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);

    private Mappers() { }
}
