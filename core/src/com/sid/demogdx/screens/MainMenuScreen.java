package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.screens.actors.OverlayMenuActor;
import com.sid.demogdx.utils.AppConfig;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveToAligned;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.sid.demogdx.utils.FontsFactory.FontName;
import static com.sid.demogdx.utils.FontsFactory.FontSize;
import static com.sid.demogdx.utils.FontsFactory.createFont;

/**
 * Created by SID on 2016-03-03 @ 18:09.
 */
public class MainMenuScreen extends AbstractScreen {
//    Stage stage;
    Label title, btnOverlay, btnGoToTarget, btnFallingStars, btnFallingBall, btnGravityBalls,
    btnBTree, btnFollowTheLine, btnCircleAround, btnDropStack, btnHitBall, btnFollowTheWave;
    Image exitBtn;

    public MainMenuScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);

        final Table table = new Table(skin);
        table.setBounds(0, 0 , AppConfig.WWP, AppConfig.WHP);

        Label.LabelStyle style = new Label.LabelStyle(createFont(FontName.FreeMonoBold, FontSize.Size32), Color.WHITE);
        style.background = skin.getDrawable("button_gold");
        title = new Label("Mover", style);
//        title = new Label("Mover", skin, "gold");
        title.setAlignment(Align.center);
        // TODO: 2016.11.09 sequence behave strangle, the 1st move action not executed, this is why I have added delay(0.001f)
        title.addAction(sequence(
                delay(0.001f),
                moveBy(0, stage.getHeight()),
                moveBy(0, -stage.getHeight(), 1.5f, Interpolation.bounce)));

        btnGoToTarget = new Label("Go to Target", style);
//        btnGoToTarget = new Label("Go to Target", skin, "gold");
        btnGoToTarget.setAlignment(Align.center);
        btnGoToTarget.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHeroScreen());
            }
        });

        btnFallingStars = new Label("Falling Stars", style);
//        btnFallingStars = new Label("Falling Stars", skin, "gold");
        btnFallingStars.setAlignment(Align.center);
        btnFallingStars.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingStarsScreen());
            }
        });

        btnFallingBall = new Label("Falling Ball", style);
//        btnFallingBall = new Label("Falling Ball", skin, "gold");
        btnFallingBall.setAlignment(Align.center);
        btnFallingBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingBallScreen());
            }
        });

        btnGravityBalls = new Label("Gravity Ball", style);
//        btnGravityBalls = new Label("Gravity Ball", skin, "gold");
        btnGravityBalls.setAlignment(Align.center);
        btnGravityBalls.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getGravityBallsScreen());
            }
        });

        btnBTree = new Label("BehaviorTree", style);
//        btnBTree = new Label("BehaviorTree", skin, "gold");
        btnBTree.setAlignment(Align.center);
        btnBTree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getBehaviorTreeScreen());
            }
        });

        btnFollowTheLine = new Label("Follow the Line", style);
//        btnFollowTheLine = new Label("Follow the Line", skin, "gold");
        btnFollowTheLine.setAlignment(Align.center);
        btnFollowTheLine.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFollowTheLineScreen());
            }
        });

        btnCircleAround = new Label("Circle Around", style);
//        btnCircleAround = new Label("Circle Around", skin, "gold");
        btnCircleAround.setAlignment(Align.center);
        btnCircleAround.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getCircleAroundScreen());
            }
        });

        btnDropStack = new Label("Drop Stack", style);
//        btnDropStack = new Label("Drop Stack", skin, "gold");
        btnDropStack.setAlignment(Align.center);
        btnDropStack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getDropStackScreen());
            }
        });

        btnHitBall = new Label("Hit a Ball", style);
//        btnHitBall = new Label("Hit a Ball", skin, "gold");
        btnHitBall.setAlignment(Align.center);
        btnHitBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHitBallScreen());
            }
        });

        btnFollowTheWave = new Label("Follow The Wave", style);
//        btnFollowTheWave = new Label("Follow The Wave", skin, "gold");
        btnFollowTheWave.setAlignment(Align.center);
        btnFollowTheWave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFollowTheWaveScreen());
            }
        });

        btnOverlay = new Label("Overlay Menu", style);
//        btnOverlay = new Label("Overlay Menu", skin, "gold");
        btnOverlay.setAlignment(Align.center);
        btnOverlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final OverlayMenuActor overlayMenuActor = new OverlayMenuActor();
                overlayMenuActor.addAction(sequence(
                        moveTo(overlayMenuActor.getX(), AppConfig.WHP + 200),
                        moveToAligned(AppConfig.WWP / 2, AppConfig.WHP / 2, Align.center, 1.5f,
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
        table.add(btnFallingStars);
        table.row().pad(2.0f);
        table.add(btnFallingBall);
        table.row().pad(2.0f);
        table.add(btnGravityBalls);
        table.row().pad(2.0f);
        table.add(btnBTree);
        table.row().pad(2.0f);
        table.add(btnFollowTheLine);
        table.row().pad(2.0f);
        table.add(btnCircleAround);
        table.row().pad(2.0f);
        table.add(btnDropStack);
        table.row().pad(2.0f);
        table.add(btnHitBall);
        table.row().pad(2.0f);
        table.add(btnFollowTheWave);
        table.row().pad(2.0f);
        table.add(btnOverlay);
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
        Gdx.input.setInputProcessor(null); /* We need this to avoid Menu click during the game
        play */
    }
}
