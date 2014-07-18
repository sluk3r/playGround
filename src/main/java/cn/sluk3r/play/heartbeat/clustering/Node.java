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

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 * Class that represents a node in a cluster.
 */
public class Node implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 177732739546836602L;

    /**
     * The address of the node.
     */
    private final InetSocketAddress inetSocketAddress;

    /**
     * The timestamp of the last received heartbeat message from this node.
     */
    private final long lastHeartbeatReceivedAt;

    /**
     * Constructor.
     * 
     * @param inetSocketAddress
     *            The address of the node.
     * @param lastHeartbeatReceivedAt
     *            The timestamp of the last received heartbeat message from this node.
     */
    public Node(final InetSocketAddress inetSocketAddress,
            final long lastHeartbeatReceivedAt) {
        super();
        if (inetSocketAddress == null) {
            throw new IllegalArgumentException("inetSocketAddress cannot be null");
        }
        this.inetSocketAddress = inetSocketAddress;
        this.lastHeartbeatReceivedAt = lastHeartbeatReceivedAt;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        if (inetSocketAddress == null) {
            if (other.inetSocketAddress != null) {
                return false;
            }
        } else if (!inetSocketAddress.equals(other.inetSocketAddress)) {
            return false;
        }
        if (lastHeartbeatReceivedAt != other.lastHeartbeatReceivedAt) {
            return false;
        }
        return true;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public long getLastHeartbeatReceivedAt() {
        return lastHeartbeatReceivedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((inetSocketAddress == null) ? 0 : inetSocketAddress.hashCode());
        result = (prime * result) + (int) (lastHeartbeatReceivedAt ^ (lastHeartbeatReceivedAt >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Node [inetSocketAddress=" + inetSocketAddress + ", lastHeartbeatReceivedAt="
                + lastHeartbeatReceivedAt + "]";
    }

}
