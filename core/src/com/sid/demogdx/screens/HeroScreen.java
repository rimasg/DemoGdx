package com.sid.demogdx.screens;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.Hero;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

/**
 * Created by SID on 2016-03-03 @ 17:18 @ 17:19.
 */
public class HeroScreen extends AbstractScreen {
    Hero actor;

    public HeroScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
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
        super.render(delta);

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
        super.resize(width, height);
    }
}
