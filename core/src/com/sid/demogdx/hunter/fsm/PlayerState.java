package com.sid.demogdx.hunter.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * Created by Okis on 2017.04.06.
 */

public enum PlayerState implements State<PlayerAgent> {
    GO_TO_TARGET() {
        @Override
        public void enter(PlayerAgent entity) {
            // TODO: 2017.04.07  need to provide Finish Location
            entity.playerComponent.steerable.getSeekAndAvoidSB().setTarget(null);
        }

        @Override
        public void update(PlayerAgent entity) {

        }

        @Override
        public void exit(PlayerAgent entity) {

        }
    },
    GO_TO_START() {
        @Override
        public void enter(PlayerAgent entity) {
            // TODO: 2017.04.07  need to provide Start Location
            entity.playerComponent.steerable.getSeekAndAvoidSB().setTarget(null);
        }

        @Override
        public void update(PlayerAgent entity) {

        }

        @Override
        public void exit(PlayerAgent entity) {

        }
    };

    @Override
    public boolean onMessage(PlayerAgent entity, Telegram telegram) {
        return false;
    }
}
