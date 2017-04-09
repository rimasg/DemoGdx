package com.sid.demogdx.hunter.pfa;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

/**
 * Created by Okis on 2017.04.08.
 */

public class TiledPathFinder {
    private TiledGraph graph;
    private DefaultGraphPath<TiledNode> path;
    private TiledManhattanDistance<TiledNode> heuristic;
    private IndexedAStarPathFinder<TiledNode> pathFinder;

    public TiledPathFinder(int sizeX, int sizeY) {
        initTiledGraph(sizeX, sizeY);
    }

    private void initTiledGraph(int sizeX, int sizeY) {
        graph = new TiledGraph(sizeX, sizeY);
        path = new DefaultGraphPath<>();
        heuristic = new TiledManhattanDistance<>();
        pathFinder = new IndexedAStarPathFinder<>(graph);
    }

    /**
     * @param start Start Node
     * @param target Target Node
     * @return true if path found; to get path call {@link #getPath()}
     */
    public boolean findPath(TiledNode start, TiledNode target) {
        return pathFinder.searchNodePath(start, target, heuristic, path);
    }

    public TiledGraph getGraph() {
        return graph;
    }

    public DefaultGraphPath<TiledNode> getPath() {
        return path;
    }
}
