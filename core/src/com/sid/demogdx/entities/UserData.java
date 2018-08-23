package com.sid.demogdx.entities;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class UserData {
    public String id;
    public UserType type;
    public TextureAtlas.AtlasRegion atlasRegion;
    public Sprite sprite;
    public ParticleEffect particleEffect;

    public UserData(String id) {
        this.id = id;
    }

    public UserData(String id, UserType type) {
        this.id = id;
        this.type = type;
    }

    public UserData(String id, UserType type, TextureAtlas.AtlasRegion atlasRegion) {
        this.id = id;
        this.type = type;
        this.atlasRegion = atlasRegion;
    }

    public UserData(String id, UserType type, Sprite sprite) {
        this.id = id;
        this.type = type;
        this.sprite = sprite;
    }

    public UserData(String id, UserType type, ParticleEffect particleEffect) {
        this.id = id;
        this.type = type;
        this.particleEffect = particleEffect;
    }

    private UserData(String id, UserType type, TextureAtlas.AtlasRegion atlasRegion, Sprite sprite, ParticleEffect
            particleEffect) {
        this.id = id;
        this.type = type;
        this.atlasRegion = atlasRegion;
        this.sprite = sprite;
        this.particleEffect = particleEffect;
    }

    public enum UserType {
        Player("player"),
        Enemy("enemy"),
        Obstacle("obstacle"),
        Wall("wall"),
        Respawn("respawn"),
        Finish("finish");

        public String type;

        UserType(String type) {
            this.type = type;
        }
    }
}
