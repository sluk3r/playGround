package cn.sluk3r.play.heartbeat.clustering;

/*
 * Copyright (c) 2013, Everit Kft.
 *
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The default implementation of the {@link NodeProvider} that gives the default node handling (adding and querying)
 * method implementations.
 */
public class DefaultNodeManager implements NodeManager {

    /**
     * The nodes belonging to this cluster.
     */
    private final Map<InetSocketAddress, Node> nodes = new ConcurrentHashMap<InetSocketAddress, Node>();

    @Override
    public Node addNode(final Node node) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        return nodes.put(node.getInetSocketAddress(), node);
    }

    @Override
    public Node[] getAllNodes() {
        return nodes.values().toArray(new Node[] {});
    }

    @Override
    public Node[] getLiveNodes(final long thresholdInMs) {
        List<Node> liveNodes = new ArrayList<Node>();
        long deadline = System.currentTimeMillis() - thresholdInMs;
        for (Node node : nodes.values()) {
            if (node.getLastHeartbeatReceivedAt() > deadline) {
                liveNodes.add(node);
            }
        }
        return liveNodes.toArray(new Node[] {});
    }

}
