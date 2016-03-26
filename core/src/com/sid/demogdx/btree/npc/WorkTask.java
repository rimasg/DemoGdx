package com.sid.demogdx.btree.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by Okis on 2016.03.26 @ 22:39.
 */
public class WorkTask extends LeafTask<NPC> {
    @Override
    public Status execute() {
        final NPC npc = getObject();
        npc.working();
        npc.decreaseStamina();
        if (npc.getStamina() < 1) {
            return Status.SUCCEEDED;
        }
        return Status.RUNNING;
    }

    @Override
    protected Task<NPC> copyTo(Task<NPC> task) {
        return null;
    }
}
