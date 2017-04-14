package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Okis on 2017.04.14.
 */

public class HealthComponent implements Component {
    public int health = 0;

    public void reset() {
        health = 0;
    }
}
