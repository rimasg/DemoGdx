package com.sid.demogdx.hunter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by SID on 2017-04-04.
 */

public class AnimationComponent implements Component {
    public IntMap<Animation<TextureRegion>> anim = new IntMap<>();
}
