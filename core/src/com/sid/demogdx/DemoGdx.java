package com.sid.demogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.screens.AirFightScreen;
import com.sid.demogdx.screens.BehaviorTreeScreen;
import com.sid.demogdx.screens.Box2DSpriteDrawTest;
import com.sid.demogdx.screens.Box2dSampleScreen;
import com.sid.demogdx.screens.CircleAroundScreen;
import com.sid.demogdx.screens.DropStackScreen;
import com.sid.demogdx.screens.FallingBallScreen;
import com.sid.demogdx.screens.FallingStarsScreen;
import com.sid.demogdx.screens.FollowTheWaveScreen;
import com.sid.demogdx.screens.GravityBallsScreen;
import com.sid.demogdx.screens.HeroScreen;
import com.sid.demogdx.screens.HitBallScreen;
import com.sid.demogdx.screens.HunterAIScreen;
import com.sid.demogdx.screens.LoadingScreen;
import com.sid.demogdx.screens.MainMenuScreen;

public class DemoGdx extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    private MainMenuScreen mainMenuScreen;
    private HeroScreen heroScreen;
    private FallingStarsScreen fallingStarsScreen;
    private Box2dSampleScreen box2dSampleScreen;
    private FallingBallScreen fallingBallScreen;
    private BehaviorTreeScreen behaviorTreeScreen;
    private GravityBallsScreen gravityBallsScreen;
    private CircleAroundScreen circleAroundScreen;
    private DropStackScreen dropStackScreen;
    private HitBallScreen hitBallScreen;
    private FollowTheWaveScreen followTheWaveScreen;
    private AirFightScreen airFightScreen;
    private HunterAIScreen hunterAIScreen;
    private Box2DSpriteDrawTest box2DSpriteDrawTest;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mainMenuScreen = new MainMenuScreen(this);
        heroScreen = new HeroScreen(this);
        fallingStarsScreen = new FallingStarsScreen(this);
        box2dSampleScreen = new Box2dSampleScreen(this);
        fallingBallScreen = new FallingBallScreen(this);
        gravityBallsScreen = new GravityBallsScreen(this);
        behaviorTreeScreen = new BehaviorTreeScreen(this);
        circleAroundScreen = new CircleAroundScreen(this);
        dropStackScreen = new DropStackScreen(this);
        hitBallScreen = new HitBallScreen(this);
        followTheWaveScreen = new FollowTheWaveScreen(this);
        airFightScreen = new AirFightScreen(this);
        hunterAIScreen = new HunterAIScreen(this);
        box2DSpriteDrawTest = new Box2DSpriteDrawTest(this);
        LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
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

    public Box2dSampleScreen getbox2dSampleScreen() {
        return box2dSampleScreen;
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

    public CircleAroundScreen getCircleAroundScreen() {
        return circleAroundScreen;
    }

    public DropStackScreen getDropStackScreen() {
        return dropStackScreen;
    }

    public HitBallScreen getHitBallScreen() {
        return hitBallScreen;
    }

    public FollowTheWaveScreen getFollowTheWaveScreen() {
        return followTheWaveScreen;
    }

    public AirFightScreen getAirFightScreen() {
        return airFightScreen;
    }

    public HunterAIScreen getHunterAIScreen() {
        return hunterAIScreen;
    }

    public Box2DSpriteDrawTest getBox2DSpriteDrawTest() {
        return box2DSpriteDrawTest;
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        Assets.inst().dispose();
    }
}
