package com.sid.demogdx.hunter.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.sid.demogdx.hunter.SeekAndAvoidSB;

/**
 * Created by Okis on 2017.04.06.
 */

public enum PlayerState implements State<PlayerAgent> {
    GO_TO_TARGET() {
        @Override
        public void enter(PlayerAgent entity) {
            final SeekAndAvoidSB seekAndAvoidSB = entity.playerComponent.steerable.getSeekAndAvoidSB();
            seekAndAvoidSB.setTarget(seekAndAvoidSB.finish);
        }

        @Override
        public void update(PlayerAgent entity) {
            if (entity.arrivedToTarget()) {
                entity.stateMachine.changeState(GO_TO_START);
            }
        }

        @Override
        public void exit(PlayerAgent entity) {

        }
    },
    GO_TO_START() {
        @Override
        public void enter(PlayerAgent entity) {
            final SeekAndAvoidSB seekAndAvoidSB = entity.playerComponent.steerable.getSeekAndAvoidSB();
            seekAndAvoidSB.setTarget(seekAndAvoidSB.start);
        }

        @Override
        public void update(PlayerAgent entity) {
            if (entity.arrivedToTarget()) {
                entity.stateMachine.changeState(GO_TO_TARGET);
            }
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
