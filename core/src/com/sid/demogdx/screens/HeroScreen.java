package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sid.demogdx.utils.AppConfig;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.Hero;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

/**
 * Created by SID on 2016-03-03 @ 17:18 @ 17:19.
 */
public class HeroScreen extends AbstractScreen {
    Stage stage;
    Hero actor;

    public HeroScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        int w = AppConfig.WORLD_WIDTH_PIXEL;
        int h = AppConfig.WORLD_HEIGHT_PIXEL;

        stage = new Stage(new FitViewport(w, h), game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final Actor hit = stage.hit(x, y, true);
                if (null == hit) {
                    moveActorToTargetPos(x, y);
                }
            }
        });

        actor = new Hero();
        actor.addListener(new DragListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                actor.moveBy(x - actor.getOriginX(), y - actor.getOriginY());
            }
        });

        stage.addActor(actor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    private void moveActorToTargetPos(float x, float y) {
        float degreesInRadians = MathUtils.atan2(
                y - actor.getY() - actor.getOriginY(),
                x - actor.getX() - actor.getOriginX());

        final float actionDuration = 1.5f;
        final RotateToAction rotateToAction = action(RotateToAction.class);
        rotateToAction.setRotation(-90f + degreesInRadians * MathUtils.radiansToDegrees);
        rotateToAction.setDuration(actionDuration);
        rotateToAction.setInterpolation(Interpolation.circleOut);

        final MoveToAction moveToAction = action(MoveToAction.class);
        moveToAction.setPosition(x - actor.getOriginX(), y - actor.getOriginY());
        moveToAction.setDuration(actionDuration);
        moveToAction.setInterpolation(Interpolation.circleOut);
        actor.addAction(parallel(rotateToAction, moveToAction));
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
