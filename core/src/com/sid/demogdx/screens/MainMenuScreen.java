package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.screens.actors.OverlayMenuActor;
import com.sid.demogdx.utils.AppConfig;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveToAligned;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by SID on 2016-03-03 @ 18:09.
 */
public class MainMenuScreen extends AbstractScreen {
//    Stage stage;
    Label title, btnPlay, btnBox2d, btnOverlay, btnFallingBall;
    Image exitBtn;

    public MainMenuScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

//        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        final Table table = new Table(skin);
        table.setBounds(0, 0 , AppConfig.WWP, AppConfig.WHP);
        title = new Label("Mover", skin, "gold");
        title.setAlignment(Align.center);

        btnPlay = new Label("Go to Target", skin, "gold");
        btnPlay.setAlignment(Align.center);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHeroScreen());
            }
        });

        btnBox2d = new Label("Falling Stars", skin, "gold");
        btnBox2d.setAlignment(Align.center);
        btnBox2d.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingStarsScreen());
            }
        });

        btnFallingBall = new Label("Falling Ball", skin, "gold");
        btnFallingBall.setAlignment(Align.center);
        btnFallingBall.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getFallingBallScreen());
            }
        });

        btnOverlay = new Label("Overlay Menu", skin, "gold");
        btnOverlay.setAlignment(Align.center);
        btnOverlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final OverlayMenuActor overlayMenuActor = new OverlayMenuActor();
                overlayMenuActor.addAction(sequence(
                        moveTo(overlayMenuActor.getX(), AppConfig.WHP + 200),
                        moveToAligned(AppConfig.WWP / 2, AppConfig.WHP / 2, Align.center, 1.0f,
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

        table.row().center();
        table.add(title).center().width(AppConfig.WWP * 0.6f);
        table.row().center().pad(20.0f);
        table.add(btnPlay);
        table.row().center().pad(20.0f);
        table.add(btnBox2d);
        table.row().center().pad(20.0f);
        table.add(btnFallingBall);
        table.row().center().pad(20.0f);
        table.add(btnOverlay);
        table.row().center().pad(20.0f);
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
    }
}
