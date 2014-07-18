package cn.sluk3r.test.collection.heartbeat.jgroup;

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

import cn.sluk3r.play.heartbeat.HeartbeatService;
import cn.sluk3r.play.heartbeat.clustering.DefaultNodeManager;
import cn.sluk3r.play.heartbeat.clustering.Node;
import cn.sluk3r.play.heartbeat.clustering.NodeManager;
import cn.sluk3r.play.heartbeat.clustering.NodeMessage;
import cn.sluk3r.play.heartbeat.jgroups.ClusteringJGroupsHeartbeatServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClusteringJGroupsHeartbeatServiceImplTest {

    private static final String HOST1 = "192.1.1.1";//TODO 可以指定多个虚拟IP？
    private static final String HOST2 = "192.1.1.2";
    private static final String HOST3 = "192.1.1.3";
    private static final NodeMessage NODE_MESSAGE1 = new NodeMessage(HOST1, 10);
    private static final NodeMessage NODE_MESSAGE2 = new NodeMessage(HOST2, 10); //wangxc nodeMessage跟Node的关系？
    private static final NodeMessage NODE_MESSAGE3 = new NodeMessage(HOST3, 10);
    private static final String CLUSTER1 = "c1";
    private static final String CLUSTER2 = "c2";
    private static final int P1 = 100;
    private static final int P2 = 200;
    private NodeManager nodeManager1 = new DefaultNodeManager();
    private NodeManager nodeManager2 = new DefaultNodeManager();
    private NodeManager nodeManager3 = new DefaultNodeManager();
    private HeartbeatService<NodeMessage> heartbeatService1;
    private HeartbeatService<NodeMessage> heartbeatService2;
    private HeartbeatService<NodeMessage> heartbeatService3;


    @Before
    public void before() throws InterruptedException {
        heartbeatService1 = new ClusteringJGroupsHeartbeatServiceImpl(nodeManager1, NODE_MESSAGE1, P1, CLUSTER1);
        heartbeatService2 = new ClusteringJGroupsHeartbeatServiceImpl(nodeManager2, NODE_MESSAGE2, P2, CLUSTER1);//wangxc 这两个cluster会不会因为是同名而加入一个集群， 即便不是一个实例？
        heartbeatService3 = new ClusteringJGroupsHeartbeatServiceImpl(nodeManager3, NODE_MESSAGE3, P1, CLUSTER2);
    }


    @After
    public void after() {
        heartbeatService1.stop();
        heartbeatService2.stop();
        heartbeatService3.stop();
    }


    @Test
    public void test() throws InterruptedException {
        heartbeatService1.start();
        Thread.sleep(P1);

        Assert.assertEquals(1, nodeManager1.getAllNodes().length); //wangxc  这个结点是从哪来的？ 并没看到有添加结点的操作。

        heartbeatService2.start();
        Thread.sleep(P2);

        Assert.assertEquals(2, nodeManager1.getAllNodes().length);
        Assert.assertEquals(2, nodeManager2.getAllNodes().length);

        heartbeatService3.start();
        Thread.sleep(P2);

        Assert.assertEquals(2, nodeManager1.getAllNodes().length);
        Assert.assertEquals(2, nodeManager2.getAllNodes().length);

        for (Node node : nodeManager1.getAllNodes()) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());

//            System.out.println("Host name: " +node.getInetSocketAddress().getHostName());

            Assert.assertTrue((node.getInetSocketAddress().getHostName().equals(HOST1))
                    || (node.getInetSocketAddress().getHostName().equals(HOST2)));
        }

        for (Node node : nodeManager2.getAllNodes()) {
            Assert.assertTrue(node.getLastHeartbeatReceivedAt() < System.currentTimeMillis());
            Assert.assertTrue((node.getInetSocketAddress().getHostName().equals(HOST1))
                    || (node.getInetSocketAddress().getHostName().equals(HOST2)));
        }
    }
}
