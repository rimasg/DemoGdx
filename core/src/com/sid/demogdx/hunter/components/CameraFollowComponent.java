package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Okis on 2017.03.26.
 */

public class CameraFollowComponent implements Component {
    public OrthographicCamera cam;
    public Entity target;
}
