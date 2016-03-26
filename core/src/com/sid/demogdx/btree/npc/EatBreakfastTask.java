package com.sid.demogdx.btree.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by Okis on 2016.03.26 @ 21:40.
 */
public class EatBreakfastTask extends LeafTask<NPC> {
    @Override
    public Status execute() {
        final NPC npc = getObject();
        npc.eatBreakfast();
        npc.increaseStamina(3);
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<NPC> copyTo(Task<NPC> task) {
        return task;
    }
}
