package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.assets.AssetDescriptors;
import com.sid.demogdx.assets.AssetsNew;
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
    Label title, lblOverlay, lblGoToTarget, lblFallingStars, lblFallingBall, lblGravityBalls,
            lblBTree, lblFollowTheLine, lblCircleAround, lblDropStack, lblHitBall, lblFollowTheWave,
            lblAirFight, lblPhysicsBody;
    TextButton btnGoToTarget, btnHunterAI;
    Image exitBtn;

    public MainMenuScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        final Table table = new Table(skin);
        table.setBounds(0, 0 , Box2DConfig.WWP, Box2DConfig.WHP);

        final BitmapFont font = AssetsNew.inst().getFont(AssetDescriptors.FONT_FREE_MONO_BOLD_32);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        style.background = skin.getDrawable("button_gold");
        title = new Label("Mover", style);
        title.setAlignment(Align.center);
        // TODO: 2016.11.09 sequence behave strangle, the 1st move action not executed, this is why I have added delay(0.001f)
        title.addAction(sequence(
                delay(0.001f),
                moveBy(0, stage.getHeight()),
                moveBy(0, -stage.getHeight(), 1.5f, Interpolation.bounce)));

        final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                skin.getDrawable("button_gold"),
                skin.getDrawable("button_gold_pressed"),
                skin.getDrawable("button_gold"),
                font
        );
        btnGoToTarget = new TextButton("Go to Target", buttonStyle);
        lblGoToTarget = new Label("Go to Target", style);
        lblGoToTarget.setAlignment(Align.center);
        lblGoToTarget.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHeroScreen());
            }
        });

        lblFallingStars = new Label("Falling Stars", style);
        lblFallingStars.setAlignment(Align.center);
        lblFallingStars.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingStarsScreen());
            }
        });

        lblFallingBall = new Label("Falling Ball", style);
        lblFallingBall.setAlignment(Align.center);
        lblFallingBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingBallScreen());
            }
        });

        lblGravityBalls = new Label("Gravity Ball", style);
        lblGravityBalls.setAlignment(Align.center);
        lblGravityBalls.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getGravityBallsScreen());
            }
        });

        lblBTree = new Label("BehaviorTree", style);
        lblBTree.setAlignment(Align.center);
        lblBTree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getBehaviorTreeScreen());
            }
        });

        lblFollowTheLine = new Label("Follow the Line", style);
        lblFollowTheLine.setAlignment(Align.center);
        lblFollowTheLine.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFollowTheLineScreen());
            }
        });

        lblCircleAround = new Label("Circle Around", style);
        lblCircleAround.setAlignment(Align.center);
        lblCircleAround.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getCircleAroundScreen());
            }
        });

        lblDropStack = new Label("Drop Stack", style);
        lblDropStack.setAlignment(Align.center);
        lblDropStack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getDropStackScreen());
            }
        });

        lblHitBall = new Label("Hit a Ball", style);
        lblHitBall.setAlignment(Align.center);
        lblHitBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHitBallScreen());
            }
        });

        lblFollowTheWave = new Label("Follow The Wave", style);
        lblFollowTheWave.setAlignment(Align.center);
        lblFollowTheWave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFollowTheWaveScreen());
            }
        });

        lblAirFight = new Label("Air Fight", style);
        lblAirFight.setAlignment(Align.center);
        lblAirFight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getAirFightScreen());
            }
        });

        lblPhysicsBody = new Label("Physics Body", style);
        lblPhysicsBody.setAlignment(Align.center);
        lblPhysicsBody.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getPhysicsBodyScreen());
            }
        });

        btnHunterAI = new TextButton("Hunter AI", buttonStyle);
        btnHunterAI.align(Align.center);
        btnHunterAI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHunterAIScreen());
            }
        });

        lblOverlay = new Label("Overlay Menu", style);
        lblOverlay.setAlignment(Align.center);
        lblOverlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final OverlayMenuActor overlayMenuActor = new OverlayMenuActor();
                overlayMenuActor.addAction(sequence(
                        moveTo(overlayMenuActor.getX(), Box2DConfig.WHP + 200),
                        moveToAligned(Box2DConfig.WWP / 2, Box2DConfig.WHP / 2, Align.center, 1.5f,
                                Interpolation.bounceOut))) ;
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
        table.setBackground(skin.getDrawable("button_black"));
        table.row().expandX().fillX();
        table.add(title);
        table.row().pad(2.0f);
        table.columnDefaults(0).width(Value.percentWidth(0.8f, table));
        table.add(btnGoToTarget);
        table.row().pad(2.0f);
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
        table.add(lblFollowTheLine);
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
        table.add(lblPhysicsBody);
        table.row().pad(2.0f);
        table.add(btnHunterAI);
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
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null); /* We need this to avoid Menu click during the game play */
    }
}
