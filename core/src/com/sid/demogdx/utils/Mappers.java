package com.sid.demogdx.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.sid.demogdx.hunter.components.Box2DMapParserComponent;
import com.sid.demogdx.hunter.components.PlayerComponent;
import com.sid.demogdx.hunter.components.TextureComponent;
import com.sid.demogdx.hunter.components.TransformComponent;

/**
 * Created by SID on 2017-03-27.
 */

public final class Mappers {
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<Box2DMapParserComponent> box2DMapParser = ComponentMapper.getFor(Box2DMapParserComponent.class);

    private Mappers() { }
}
