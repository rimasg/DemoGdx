package com.sid.demogdx.entities.wave;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.entities.GameObject;

/**
 * Created by Okis on 2016.10.12.
 */

public class Runner extends GameObject {
    private ParticleEffect particleEffect;

    public Runner(Sprite sprite) {
        super(sprite);
        particleEffect = Assets.getParticleEffect(AssetDescriptors.PARTICLE_EFFECT_SIMPLE_TRAIL);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        particleEffect.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        particleEffect.setPosition(pos.x, pos.y);
        particleEffect.draw(batch);
    }
}
