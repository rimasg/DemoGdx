package com.sid.demogdx.screens;

import com.badlogic.gdx.Screen;
import com.sid.demogdx.DemoGdx;

/**
 * Created by SID on 2016-03-03 @ 17:13 @ 17:19.
 */
public abstract class AbstractScreen implements Screen {

    final DemoGdx game;

    public AbstractScreen(final DemoGdx game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
