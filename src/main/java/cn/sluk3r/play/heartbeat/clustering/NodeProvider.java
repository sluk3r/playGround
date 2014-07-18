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

/**
 * An interface that supports clustering functionalities, i.e. querying the {@link Node}s that are in the same cluster
 * as the current one.
 */
public interface NodeProvider {

    /**
     * Queries all the {@link Node}s that are in the same cluster as the current one.
     * 
     * @return The {@link Node}s.
     */
    Node[] getAllNodes();

    /**
     * Queries the {@link Node}s that fulfills the following criteria:
     * <ul>
     * <li>The timestamp of the last heartbeat message received from the node is greater than
     * <code>(System.currentTimeMillis() - thresholdInMs)</code>.</li>
     * </ul>
     * 
     * @param thresholdInMs
     *            The threshold in milliseconds.
     * @return The {@link Node}s.
     */
    Node[] getLiveNodes(long thresholdInMs);

}
