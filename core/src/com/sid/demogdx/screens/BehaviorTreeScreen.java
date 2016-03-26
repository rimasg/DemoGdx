package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.btree.npc.NPC;

/**
 * Created by Okis on 2016.03.26 @ 21:50.
 */
public class BehaviorTreeScreen extends AbstractScreen {
    public static final float AI_STEP_DELAY = 1.5f;
    NPC npc;

    public BehaviorTreeScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        npc = new NPC();
    }

    private float runDelayAccumulator;

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        runDelayAccumulator += delta;
        if (runDelayAccumulator > AI_STEP_DELAY) {
            // Update AI time
            GdxAI.getTimepiece().update(delta);

            // Update behavior trees
            npc.btree.step();
            runDelayAccumulator = 0;
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

        @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
