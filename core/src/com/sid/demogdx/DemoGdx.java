package com.sid.demogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sid.demogdx.screens.FallingBallScreen;
import com.sid.demogdx.screens.FallingStarsScreen;
import com.sid.demogdx.screens.HeroScreen;
import com.sid.demogdx.screens.MainMenuScreen;

public class DemoGdx extends Game {

    public SpriteBatch batch;
    HeroScreen heroScreen;
    MainMenuScreen mainMenuScreen;
    FallingStarsScreen fallingStarsScreen;
    FallingBallScreen fallingBallScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        loadAssets();
        heroScreen = new HeroScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        fallingStarsScreen = new FallingStarsScreen(this);
        fallingBallScreen = new FallingBallScreen(this);
        setScreen(mainMenuScreen);
    }

    private void loadAssets() {
        Assets.inst().load("skin.json", Skin.class, new SkinLoader.SkinParameter("textures/texture.pack"));
        Assets.inst().finishLoading();
    }

    public HeroScreen getHeroScreen() {
        return heroScreen;
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public FallingStarsScreen getFallingStarsScreen() {
        return fallingStarsScreen;
    }

    public FallingBallScreen getFallingBallScreen() {
        return fallingBallScreen;
    }

    @Override
    public void render() {
        super.render();
    }
}
