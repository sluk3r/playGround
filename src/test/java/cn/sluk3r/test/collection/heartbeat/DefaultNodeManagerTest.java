package cn.sluk3r.test.collection.heartbeat;

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

import cn.sluk3r.play.heartbeat.clustering.DefaultNodeManager;
import cn.sluk3r.play.heartbeat.clustering.Node;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Class to test the DefaultNodeManager class.
 */
public class DefaultNodeManagerTest {

    /**
     * Testing the addNode() and getAllNodes() function of the DefaultNodeManager class.
     * 
     * @throws java.net.UnknownHostException
     *             If can't get InetAddress.
     */
    @Test
    public void addAndGetNodeTest() throws UnknownHostException {
        DefaultNodeManager defaultNodeManager = new DefaultNodeManager();

        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.1.1", 400);

        Node node1 = new Node(inetSocketAddress, 111);
        Node node2 = new Node(inetSocketAddress, 222);

        Assert.assertNull(defaultNodeManager.addNode(node1));

        Assert.assertEquals(node1, defaultNodeManager.addNode(node2));

        Assert.assertFalse(Arrays.asList(defaultNodeManager.getAllNodes()).contains(node1));
        Assert.assertTrue(Arrays.asList(defaultNodeManager.getAllNodes()).contains(node2));

        Node node3 = new Node(InetSocketAddress.createUnresolved("192.168.1.2", 400), 222);
        defaultNodeManager.addNode(node3);
        Node node4 = new Node(InetSocketAddress.createUnresolved("192.168.1.3", 400), 333);
        defaultNodeManager.addNode(node4);

        Assert.assertTrue(Arrays.asList(defaultNodeManager.getAllNodes()).size() == 3);

    }

    /**
     * Testing the addNode method with null parameter. Should throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void addNullTest() {
        DefaultNodeManager defaultNodeManager = new DefaultNodeManager();
        defaultNodeManager.addNode(null);
    }

    /**
     * Testing the getLiveNodes() method.
     *
     * @throws java.net.UnknownHostException
     *             If can't get InetAddress.
     */
    @Test
    public void getLiveNodesTest() throws UnknownHostException {
        DefaultNodeManager defaultNodeManager = new DefaultNodeManager();

        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.1.1", 400);
        long thresholdInMs = 2000;
        long lastHeartbeatReveivedAt = System.currentTimeMillis();

        Node node1 = new Node(inetSocketAddress, 100);
        Node node2 = new Node(InetSocketAddress.createUnresolved("192.168.1.2", 400), lastHeartbeatReveivedAt);
        Node node3 = new Node(InetSocketAddress.createUnresolved("192.168.1.3", 400), lastHeartbeatReveivedAt
                - thresholdInMs);
        defaultNodeManager.addNode(node1);
        defaultNodeManager.addNode(node2);
        defaultNodeManager.addNode(node3);

        List<Node> liveNodes = Arrays.asList(defaultNodeManager.getLiveNodes(thresholdInMs));
        for (Node i : liveNodes) {
            Assert.assertTrue(i.getLastHeartbeatReceivedAt() > (lastHeartbeatReveivedAt - thresholdInMs));
        }

    }

}
