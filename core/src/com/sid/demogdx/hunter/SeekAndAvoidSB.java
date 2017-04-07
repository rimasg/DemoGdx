package com.sid.demogdx.hunter;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sid.demogdx.utils.Box2dRaycastCollisionDetector;

/**
 * Created by Okis on 2017.03.25.
 */

public final class SeekAndAvoidSB {

    private PrioritySteering<Vector2> steering;
    private RayConfigurationBase<Vector2> rayConfiguration;
    private Seek<Vector2> seekSB;
    public Location<Vector2> start;
    public Location<Vector2> finish;

    public SeekAndAvoidSB initSteering(World world, Steerable<Vector2> owner, Location<Vector2> start, Location<Vector2> target) {
        this.start = start;
        this.finish = target;
        rayConfiguration = new CentralRayWithWhiskersConfiguration<>(owner, 1.f, 0.8f, 35 * MathUtils.degreesToRadians);
        RaycastCollisionDetector<Vector2> raycastCollisionDetector = new Box2dRaycastCollisionDetector(world);
        //
        final RaycastObstacleAvoidance<Vector2> avoidanceSB = new RaycastObstacleAvoidance<>(owner, rayConfiguration, raycastCollisionDetector);
        //
        seekSB = new Seek<>(owner, finish);
        //
        steering = new PrioritySteering<>(owner, 0.0001f)
                .add(avoidanceSB)
                .add(seekSB);
        return this;
    }

    public Seek<Vector2> setTarget(Location<Vector2> target) {
        return seekSB.setTarget(target);
    }

    public Location<Vector2> getTarget() {
        return seekSB.getTarget();
    }

    public PrioritySteering<Vector2> getSteering() {
        return steering;
    }

    public Ray<Vector2>[] getRays() {
        return rayConfiguration.getRays();
    }
}
