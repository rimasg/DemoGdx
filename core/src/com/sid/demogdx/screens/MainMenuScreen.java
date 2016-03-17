package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sid.demogdx.Assets;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.screens.actors.OverlayMenuActor;
import com.sid.demogdx.utils.AppConfig;

/**
 * Created by SID on 2016-03-03 @ 18:09.
 */
public class MainMenuScreen extends AbstractScreen {

    Stage stage;
    Label title, btnPlay, btnBox2d, btnOverlay;
    Image exitBtn;

    public MainMenuScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        int w = AppConfig.WORLD_WIDTH_PIXEL;
        int h = AppConfig.WORLD_HEIGHT_PIXEL;

        stage = new Stage(new FitViewport(w, h), game.batch);
        Gdx.input.setInputProcessor(stage);

        final Skin skin = Assets.inst().get("skin.json", Skin.class);

        final Table table = new Table(skin);
        table.setBounds(0, 0 , AppConfig.WORLD_WIDTH_PIXEL, AppConfig.WORLD_HEIGHT_PIXEL);
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

        btnBox2d = new Label("Box2d Mover", skin, "gold");
        btnBox2d.setAlignment(Align.center);
        btnBox2d.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getBox2dScreen());
            }
        });

        btnOverlay = new Label("Overlay Menu", skin, "gold");
        btnOverlay.setAlignment(Align.center);
        btnOverlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final OverlayMenuActor overlayMenuActor = new OverlayMenuActor();
                overlayMenuActor.addAction(Actions.sequence(
                        Actions.moveTo(overlayMenuActor.getX(), AppConfig.WORLD_HEIGHT_PIXEL + 200),
                        Actions.moveToAligned(AppConfig.WORLD_WIDTH_PIXEL / 2, AppConfig.WORLD_HEIGHT_PIXEL / 2, Align.center, 1.0f,
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
        table.add(title).center().width(AppConfig.WORLD_WIDTH_PIXEL * 0.6f);
        table.row().center().pad(20.0f);
        table.add(btnPlay);
        table.row().center().pad(20.0f);
        table.add(btnBox2d);
        table.row().center().pad(20.0f);
        table.add(btnOverlay);
        table.row().center().pad(20.0f);
        table.add(exitBtn);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}
