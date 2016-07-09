package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.btree.npc.NPC;

import java.nio.ByteBuffer;

/**
 * Created by Okis on 2016.03.26 @ 21:50.
 */
public class BehaviorTreeScreen extends AbstractScreen {
    public static final float AI_STEP_DELAY = 1.5f;
    NPC npc;
    Texture badlogic;

    public BehaviorTreeScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        npc = new NPC();
        badlogic = new Texture("badlogic.jpg");
    }

    private float runDelayAccumulator;

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();
//        updateAI(delta);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(badlogic,
                cam.viewportWidth * 0.2f,
                cam.viewportHeight * 0.2f,
                cam.viewportWidth * 0.6f,
                cam.viewportHeight * 0.6f);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
        if (Gdx.input.justTouched()) {
            Gdx.app.log(TAG, "handleInput: Coords:" +
                    " X -> " + Gdx.input.getX() +
                    ", Y -> " + (Gdx.graphics.getHeight() - Gdx.input.getY() - 1));
            getPixelColor(
                    Gdx.input.getX(),
                    Gdx.graphics.getHeight() - Gdx.input.getY() - 1);
        }
    }

    /**
     * Method to read Pixel color by x, y coordinates
     * @param x coordinate
     * @param y coordinate
     */
    private void getPixelColor(int x, int y) {
        ByteBuffer pixels = ByteBuffer.allocateDirect(4);
//        pixels.order(ByteOrder.nativeOrder());
//        pixels.position(0);
        for (int i = 0; i < 300; i += 5) {
            Gdx.gl.glReadPixels(
                    i,
                    i,
                    1, 1, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);
            // TODO: 2016.07.02 how to get pixel color?
            int r = pixels.get(); if (0 > r) r += 256;
            int g = pixels.get(); if (0 > g) g += 256;
            int b = pixels.get(); if (0 > b) b += 256;
            int a = pixels.get(); if (0 > a) a += 256;
            pixels.position(0);
            Gdx.app.log(TAG, "getPixelColor:R: " + r + " G: " + g + " B: " + b + " A: " + a);
        }
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
        badlogic.dispose();
    }
}
