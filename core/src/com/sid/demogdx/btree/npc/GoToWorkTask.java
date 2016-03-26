package com.sid.demogdx.btree.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by Okis on 2016.03.26 @ 21:44.
 */
public class GoToWorkTask extends LeafTask<NPC> {
    @Override
    public Status execute() {
        final NPC npc = getObject();
        npc.goToWork();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<NPC> copyTo(Task<NPC> task) {
        return task;
    }
}
