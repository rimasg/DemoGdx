package com.sid.demogdx.hunter.pfa;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * Created by Okis on 2017.04.08.
 */

public class TiledManhattanDistance<N extends TiledNode> implements Heuristic<N> {

    @Override
    public float estimate(N node, N endNode) {
        return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    }
}
