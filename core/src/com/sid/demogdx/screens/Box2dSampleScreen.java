package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.BodyEditorLoader;
import com.sid.demogdx.utils.Box2DConfig;
import com.sid.demogdx.utils.Box2DUtils;

public class Box2dSampleScreen extends AbstractBox2dScreen{
    public Box2dSampleScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        cam.update();
        b2dr.render(world, cam.combined);
    }

    @Override
    protected void loadAssets() {

    }

    @Override
    protected void init() {
        super.init();

        Box2DUtils.createWorldBoundaries(world);
        loadBox2dBody();
    }

    private void loadBox2dBody() {
        final BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("box2d/box2d_scene.json"));

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(Box2DConfig.WWV / 2f, Box2DConfig.WHV * 0.6f);
        final Body body = world.createBody(bd);

        final FixtureDef fd = new FixtureDef();
        fd.density = 1f;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        loader.attachFixture(body, "shape", fd,  2.0f);
    }
}
