package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.Assets;
import com.sid.demogdx.assets.RegionNames;
import com.sid.demogdx.screens.actors.OverlayMenuActor;
import com.sid.demogdx.utils.Box2DConfig;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveToAligned;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by SID on 2016-03-03 @ 18:09.
 */
public class MainMenuScreen extends AbstractScreen {
    Label title;
    Label lblOverlay;
    Label lblGoToTarget;
    Label lblFallingStars;
    Label lblFallingBall;
    Label lblGravityBalls;
    Label lblBTree;
    Label lblCircleAround;
    Label lblDropStack;
    Label lblHitBall;
    Label lblFollowTheWave;
    Label lblAirFight;
    TextButton btnHunterAI;
    TextButton btnBox2DSpriteDrawTest;
    Image exitBtn;

    private ShaderProgram shader;

    public MainMenuScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        final Table table = new Table(skin);
        table.setBounds(0, 0 , Box2DConfig.WWP, Box2DConfig.WHP);

        final BitmapFont font = Assets.inst().getFont(AssetDescriptors.FONT_OPEN_SANS_REGULAR_26);
        final I18NBundle strings = Assets.inst().getStrings();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        style.background = skin.getDrawable(RegionNames.BTN_GOLD);
        title = new Label(strings.get("gameName"), skin, "red");
//        title = new Label("Mover", style);
        title.setAlignment(Align.center);
        // TODO: 2016.11.09 sequence behave strangle, the 1st move action not executed, this is why I have added delay(0.001f)
        title.addAction(sequence(
                delay(0.001f),
                moveBy(0, stage.getHeight()),
                moveBy(0, -stage.getHeight(), 1.5f, Interpolation.bounce)));

        lblGoToTarget = new Label("Go to Target", skin, "blue");
        lblGoToTarget.setAlignment(Align.center);
        lblGoToTarget.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHeroScreen());
            }
        });

        lblFallingStars = new Label("Falling Stars", skin, "blue");
        lblFallingStars.setAlignment(Align.center);
        lblFallingStars.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingStarsScreen());
            }
        });

        lblFallingBall = new Label("Falling Ball", skin, "blue");
        lblFallingBall.setAlignment(Align.center);
        lblFallingBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingBallScreen());
            }
        });

        lblGravityBalls = new Label("Gravity Ball", skin, "blue");
        lblGravityBalls.setAlignment(Align.center);
        lblGravityBalls.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getGravityBallsScreen());
            }
        });

        lblBTree = new Label("BehaviorTree", skin, "blue");
        lblBTree.setAlignment(Align.center);
        lblBTree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getBehaviorTreeScreen());
            }
        });

        lblCircleAround = new Label("Circle Around", skin, "blue");
        lblCircleAround.setAlignment(Align.center);
        lblCircleAround.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getCircleAroundScreen());
            }
        });

        lblDropStack = new Label("Drop Stack", skin, "blue");
        lblDropStack.setAlignment(Align.center);
        lblDropStack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getDropStackScreen());
            }
        });

        lblHitBall = new Label("Hit a Ball", skin, "blue");
        lblHitBall.setAlignment(Align.center);
        lblHitBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHitBallScreen());
            }
        });

        lblFollowTheWave = new Label("Follow The Wave", skin, "blue");
        lblFollowTheWave.setAlignment(Align.center);
        lblFollowTheWave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFollowTheWaveScreen());
            }
        });

        lblAirFight = new Label("Air Fight", skin, "blue");
        lblAirFight.setAlignment(Align.center);
        lblAirFight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getAirFightScreen());
            }
        });

        btnHunterAI = new TextButton("Hunter AI", skin, "red");
        btnHunterAI.align(Align.center);
        btnHunterAI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHunterAIScreen());
            }
        });

        btnBox2DSpriteDrawTest = new TextButton("Box2DSpriteDrawTest", skin, "blue");
        btnBox2DSpriteDrawTest.align(Align.center);
        btnBox2DSpriteDrawTest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getBox2DSpriteDrawTest());
            }
        });

        lblOverlay = new Label("Overlay Menu", skin, "blue");
        lblOverlay.setAlignment(Align.center);
        lblOverlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final OverlayMenuActor overlayMenuActor = new OverlayMenuActor();
                overlayMenuActor.addAction(sequence(
                        moveTo(overlayMenuActor.getX(), Box2DConfig.WHP + 200),
                        moveToAligned(Box2DConfig.WWP / 2, Box2DConfig.WHP / 2, Align.center, 0.5f,
                                Interpolation.sineOut))) ;
                stage.addActor(overlayMenuActor);
            }
        });

        exitBtn = new Image(skin.getDrawable("exit"));
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        table.setDebug(false);
        table.setBackground(skin.getDrawable(RegionNames.BTN_BLACK));
        table.row().expandX().fillX();
        table.add(title);
        table.row().pad(2.0f);
        table.columnDefaults(0).width(Value.percentWidth(0.8f, table));
        table.add(lblGoToTarget);
        table.row().pad(2.0f);
        table.add(lblFallingStars);
        table.row().pad(2.0f);
        table.add(lblFallingBall);
        table.row().pad(2.0f);
        table.add(lblGravityBalls);
        table.row().pad(2.0f);
        table.add(lblBTree);
        table.row().pad(2.0f);
        table.add(lblCircleAround);
        table.row().pad(2.0f);
        table.add(lblDropStack);
        table.row().pad(2.0f);
        table.add(lblHitBall);
        table.row().pad(2.0f);
        table.add(lblFollowTheWave);
        table.row().pad(2.0f);
        table.add(lblAirFight);
        table.row().pad(2.0f);
        table.add(btnHunterAI);
        table.row().pad(2.0f);
        table.add(btnBox2DSpriteDrawTest);
        table.row().pad(2.0f);
        table.add(lblOverlay);
        table.row().pad(10.0f);
        table.columnDefaults(0).reset();
        table.add(exitBtn);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    protected void loadAssets() {

    }

    @Override
    protected void init() {
        initShader();
    }

    private void initShader() {
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/vignette.vert"), Gdx.files.internal("shaders/vignette.frag"));
        game.batch.setShader(shader);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        onResizeShader(width, height);
    }

    private void onResizeShader(int width, int height) {
        shader.begin();
        shader.setUniformf("u_resolution", width, height);
        shader.end();
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null); /* We need this to avoid Menu click during the game play */
    }
}
