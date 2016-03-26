package com.sid.demogdx.btree.npc;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by Okis on 2016.03.26 @ 21:42.
 */
public class DrinkCoffeeTask extends LeafTask<NPC> {
    @Override
    public Status execute() {
        final NPC npc = getObject();
        npc.drinkCoffee();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<NPC> copyTo(Task<NPC> task) {
        return task;
    }
}
