package com.sid.demogdx.btree.npc;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.BranchTask;
import com.badlogic.gdx.ai.btree.branch.RandomSelector;
import com.badlogic.gdx.ai.btree.branch.Sequence;

/**
 * Created by Okis on 2016.03.26 @ 21:13.
 */
public class NPC {
    private static final String TAG = "NPC";
    public BehaviorTree<NPC> btree;

    // Actor's attributes
    private int stamina;

    public NPC() {
        btree = new BehaviorTree<NPC>();
        initBehaviorTree();
    }

    private void initBehaviorTree() {
        final BranchTask<NPC> branchRoot = new Sequence<>();
        final BranchTask<NPC> branchRandomDrinkSelector = new RandomSelector<>();
        branchRandomDrinkSelector.addChild(new DrinkTeaTask());
        branchRandomDrinkSelector.addChild(new DrinkCoffeeTask());

        branchRoot.addChild(new WakeUpTask());
        branchRoot.addChild(new EatBreakfastTask());
        branchRoot.addChild(branchRandomDrinkSelector);
        branchRoot.addChild(new GoToWorkTask());
        branchRoot.addChild(new WorkTask());
        branchRoot.addChild(new GoHomeTask());
        btree.addChild(branchRoot);
        btree.setObject(this);
    }

    public void wakeUp() {
        log("Good morning. Wake up.");
    }

    public void eatBreakfast() {
        log("I'm eating breakfast.");
    }

    public void drinkTea() {
        log("Drinking tea.");
    }

    public void drinkCoffee() {
        log("Drinking coffee.");
    }

    public void goToWork() {
        log("Going to work.");
    }

    public void working() {
        log("Working");
    }

    public void goHome() {
        log("Going home.");
    }

    public void decreaseStamina() {
        stamina--;
    }

    public void increaseStamina(int stamina) {
        this.stamina += stamina;
    }

    public int getStamina() {
        return stamina;
    }

    public void log(String msg) {
        GdxAI.getLogger().info(TAG, msg);
    }
}
