package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sid.demogdx.Assets;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.utils.AppConfig;

/**
 * Created by SID on 2016-03-03 @ 18:09.
 */
public class MainMenuScreen extends AbstractScreen {

    Stage stage;
    Label title, playBtn, box2dBtn;
    Image exitBtn;

    public MainMenuScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        int w = AppConfig.WORLD_WIDTH_PIXEL;
        int h = AppConfig.WORLD_HEIGHT_PIXEL;

        stage = new Stage(new FitViewport(w, h), game.batch);
        Gdx.input.setInputProcessor(stage);

        final Skin skin = Assets.inst().get("skin.json", Skin.class);

        final Table table = new Table(skin);
        table.setBounds(0, 0 , AppConfig.WORLD_WIDTH_PIXEL, AppConfig.WORLD_HEIGHT_PIXEL);
        title = new Label("Mover", skin, "gold");
        title.setAlignment(Align.center);

        playBtn = new Label("Go to Target", skin, "gold");
        playBtn.setAlignment(Align.center);
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getHeroScreen());
            }
        });

        box2dBtn = new Label("Box2d Mover", skin, "gold");
        box2dBtn.setAlignment(Align.center);
        box2dBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.getBox2dScreen());
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
        table.add(playBtn);
        table.row().center().pad(20.0f);
        table.add(box2dBtn);
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
