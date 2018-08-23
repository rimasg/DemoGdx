package com.sid.demogdx.screens;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.btree.npc.NPC;

/**
 * Created by Okis on 2016.03.26 @ 21:50.
 */
public class BehaviorTreeScreen extends AbstractScreen {
    public static final float AI_STEP_DELAY = 1.5f;
    NPC npc;
    TextureRegion badlogic;
    private float runDelayAccumulator;

    public BehaviorTreeScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        npc = new NPC();
        badlogic = Assets.inst().getRegion(RegionNames.HERO);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        updateAI(delta);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(badlogic,
                cam.viewportWidth * 0.2f,
                cam.viewportHeight * 0.2f,
                cam.viewportWidth * 0.6f,
                cam.viewportHeight * 0.6f);
        game.batch.end();
    }

    @Override
    protected void loadAssets() {

    }

    @Override
    protected void init() {

    }

    private void updateAI(float delta) {
        runDelayAccumulator += delta;
        if (runDelayAccumulator > AI_STEP_DELAY) {
            // Update AI time
            GdxAI.getTimepiece().update(delta);

            // Update behavior trees
            npc.btree.step();
            runDelayAccumulator = 0;
        }
    }

}
