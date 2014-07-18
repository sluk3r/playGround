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

import cn.sluk3r.play.heartbeat.clustering.Node;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Test class for class Node.
 */
public class NodeTest {

    /**
     * Test the constructor of the Node class for null parameter.
     */
    @Test
    public void testConstructorInetAddressNullArg() {
        try {
            new Node(null, 0);
            Assert.fail("constructor should fail with IllegalArgumentException in case of null inetAddress");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test the constructor of the Node class with valid parameters.
     * 
     * @throws java.net.UnknownHostException
     *             If can't get InetAddress.
     */
    @Test
    public void testSuccess() throws UnknownHostException {
        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.1.1", 500);
        long lastHeartbeatReceivedAt = 123;
        Node node = new Node(inetSocketAddress, lastHeartbeatReceivedAt);

        Assert.assertNotNull(node);
        Assert.assertEquals(inetSocketAddress, node.getInetSocketAddress());
        Assert.assertEquals(lastHeartbeatReceivedAt, node.getLastHeartbeatReceivedAt());

    }

}
