package com.sid.demogdx.hunter.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2017.04.08.
 */

public class TiledNode {
    public int x;
    public int y;
    public Tile type = Tile.FLOOR;
    private TiledGraph graph;
    private Array<Connection<TiledNode>> connections;

    public TiledNode(TiledGraph graph, int x, int y, Tile type) {
        this.graph = graph;
        this.x = x;
        this.y = y;
        this.type = type;
        this.connections = new Array<>(4);
    }

    public Array<Connection<TiledNode>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return x * graph.sizeY + y;
    }

    public enum Tile {
        EMPTY, FLOOR, OBSTACLE
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
