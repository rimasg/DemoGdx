package com.sid.demogdx.hunter.fsm;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.math.Vector2;
import com.sid.demogdx.hunter.components.PlayerComponent;

/**
 * Created by Okis on 2017.04.06.
 */

public class PlayerAgent {

    public final PlayerComponent playerComponent;
    public final DefaultStateMachine<PlayerAgent, PlayerState> stateMachine;
    private final float proximity = 1.0f;

    public PlayerAgent(PlayerComponent playerComponent) {
        this.playerComponent = playerComponent;
        stateMachine = new DefaultStateMachine<>(this, PlayerState.GO_TO_TARGET);
    }

    public void update(float delta) {
        stateMachine.update();
    }

    public boolean arrivedToTarget() {
        final Vector2 currPos = playerComponent.body.getPosition();
        final Vector2 targetPos = playerComponent.steerable.getSeekAndAvoidSB().getTarget().getPosition();
        return currPos.dst2(targetPos) < proximity * proximity;
    }
}
