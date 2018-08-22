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

    private UserData(String id, UserType type, TextureAtlas.AtlasRegion atlasRegion, Sprite sprite, ParticleEffect
            particleEffect) {
        this.id = id;
        this.type = type;
        this.atlasRegion = atlasRegion;
        this.sprite = sprite;
        this.particleEffect = particleEffect;
    }

    public static class Builder {
        private String id;
        private UserType type;
        private TextureAtlas.AtlasRegion atlasRegion;
        private Sprite sprite;
        private ParticleEffect particleEffect;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setType(UserType type) {
            this.type = type;
            return this;
        }

        public Builder setAtlasRegion(TextureAtlas.AtlasRegion atlasRegion) {
            this.atlasRegion = atlasRegion;
            return this;
        }

        public Builder setSprite(Sprite sprite) {
            this.sprite = sprite;
            return this;
        }

        public Builder setParticleEffect(ParticleEffect particleEffect) {
            this.particleEffect = particleEffect;
            return this;
        }

        public UserData build() {
            return new UserData(id, type, atlasRegion, sprite, particleEffect);
        }
    }

    public enum UserType {
        Player("player"),
        Enemy("enemy"),
        Obstacle("obstacle"),
        Finish("finish"),
        Respawn("respawn");

        public String type;

        UserType(String type) {
            this.type = type;
        }
    }
}
1