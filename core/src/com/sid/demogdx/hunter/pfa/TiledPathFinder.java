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
    }

    /**
     * Call only after TiledGraph has been set up
     */
    public void initPathFinder() {
        pathFinder = new IndexedAStarPathFinder<>(graph);
    }

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
