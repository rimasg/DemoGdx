package com.sid.demogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sid.demogdx.screens.HeroScreen;

public class DemoGdx extends Game {

    public SpriteBatch batch;
    Screen heroScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        heroScreen = new HeroScreen(this);
        setScreen(heroScreen);
    }

    @Override
    public void render() {
        super.render();
    }
}
