package com.sid.demogdx.utils.entity;

import com.badlogic.ashley.core.Entity;

/**
 * Created by Okis on 2017.04.20.
 */

public final class EntityUtils {
    public static boolean isPlayer(Entity entity) {
        return Mappers.player.has(entity);
    }

    public static boolean isEnemy(Entity entity) {
        return Mappers.enemy.has(entity);
    }

    private EntityUtils() { }
}
