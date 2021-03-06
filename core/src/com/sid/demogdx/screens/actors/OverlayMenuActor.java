package com.sid.demogdx.screens.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sid.demogdx.Assets;

/**
 * Created by Okis on 2016.03.17 @ 22:02.
 */
public class OverlayMenuActor extends Table {

    public OverlayMenuActor() {
        final Skin skin = Assets.inst().get("skin.json", Skin.class);
        setSkin(skin);
        background(skin.getDrawable("bg_gold"));
        setSize(400.0f, 200.0f);
        center();

        final Label label = new Label("Overlay menu", skin);
        final Label btnExit = new Label("EXIT", skin);
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OverlayMenuActor.this.remove();
            }
        });

        row().center();
        add(label).center();
        row().center().pad(20.0f);
        add(btnExit).center();
        row().center().pad(20.0f);
    }
}
