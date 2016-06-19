package com.sid.demogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sid.demogdx.screens.BehaviorTreeScreen;
import com.sid.demogdx.screens.CircleAroundScreen;
import com.sid.demogdx.screens.FallingBallScreen;
import com.sid.demogdx.screens.FallingStarsScreen;
import com.sid.demogdx.screens.FollowTheLineScreen;
import com.sid.demogdx.screens.GravityBallsScreen;
import com.sid.demogdx.screens.HeroScreen;
import com.sid.demogdx.screens.MainMenuScreen;
import com.sid.demogdx.screens.DropStackScreen;

public class DemoGdx extends Game {

    public SpriteBatch batch;
    HeroScreen heroScreen;
    MainMenuScreen mainMenuScreen;
    FallingStarsScreen fallingStarsScreen;
    FallingBallScreen fallingBallScreen;
    BehaviorTreeScreen behaviorTreeScreen;
    GravityBallsScreen gravityBallsScreen;
    FollowTheLineScreen followTheLineScreen;
    CircleAroundScreen circleAroundScreen;
    DropStackScreen dropStackScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        loadAssets();
        mainMenuScreen = new MainMenuScreen(this);
        heroScreen = new HeroScreen(this);
        fallingStarsScreen = new FallingStarsScreen(this);
        fallingBallScreen = new FallingBallScreen(this);
        gravityBallsScreen = new GravityBallsScreen(this);
        behaviorTreeScreen = new BehaviorTreeScreen(this);
        followTheLineScreen = new FollowTheLineScreen(this);
        circleAroundScreen = new CircleAroundScreen(this);
        dropStackScreen = new DropStackScreen(this);
        setScreen(mainMenuScreen);
    }

    private void loadAssets() {
        Assets.inst();
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

    public GravityBallsScreen getGravityBallsScreen() {
        return gravityBallsScreen;
    }

    public BehaviorTreeScreen getBehaviorTreeScreen() {
        return behaviorTreeScreen;
    }

    public FollowTheLineScreen getFollowTheLineScreen() {
        return followTheLineScreen;
    }

    public CircleAroundScreen getCircleAroundScreen() {
        return circleAroundScreen;
    }

    public DropStackScreen getDropStackScreen() {
        return dropStackScreen;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
