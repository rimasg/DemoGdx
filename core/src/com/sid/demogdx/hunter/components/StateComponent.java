package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by SID on 2017-04-13.
 */

public class StateComponent implements Component {
    public int state = 0;
    public float time = 0.0f;

    public void setState(int state) {
        this.state = state;
        this.time = 0.0f;
    }

    public void resetState() {
        this.state = 0;
        this.time = 0.0f;
    }
}
