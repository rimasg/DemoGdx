package com.sid.demogdx.hunter.fsm;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.sid.demogdx.hunter.components.PlayerComponent;

/**
 * Created by Okis on 2017.04.06.
 */

public class PlayerAgent {

    public final PlayerComponent playerComponent;
    public final DefaultStateMachine<PlayerAgent, PlayerState> stateMachine;

    public PlayerAgent(PlayerComponent playerComponent) {
        this.playerComponent = playerComponent;
        stateMachine = new DefaultStateMachine<>(this, PlayerState.GO_TO_START);
    }

    public void update(float delta) {
        stateMachine.update();
    }

    public DefaultStateMachine<PlayerAgent, PlayerState> getStateMachine() {
        return stateMachine;
    }
}
