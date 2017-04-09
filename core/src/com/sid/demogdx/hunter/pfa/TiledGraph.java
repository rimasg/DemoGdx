package com.sid.demogdx.hunter.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Okis on 2017.04.08.
 */

public class TiledGraph implements IndexedGraph<TiledNode> {
    final int sizeX;
    final int sizeY;

    public final Array<TiledNode> nodes;

    public TiledGraph(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.nodes = new Array<>(sizeX * sizeY);
        initGraph();
    }

    private void initGraph() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                nodes.add(new TiledNode(this, x, y, TiledNode.Tile.FLOOR));
            }
        }
    }

    @Override
    public int getIndex(TiledNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<TiledNode>> getConnections(TiledNode fromNode) {
        return fromNode.getConnections();
    }

    public TiledNode getNode(int index) {
        return nodes.get(index);
    }

    public TiledNode getNode(int x, int y) {
        return nodes.get(x * sizeY + y);
    }

    public void addConnection(TiledNode node, int xOffset, int yOffset) {
        final TiledNode target = getNode(node.x + xOffset, node.y + yOffset);
        if (target.type == TiledNode.Tile.FLOOR) {
            node.getConnections().add(new DefaultConnection<>(node, target));
        }
    }
}
