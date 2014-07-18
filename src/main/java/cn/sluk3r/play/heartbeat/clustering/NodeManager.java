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
 * An interface which provides functionality to add new Node to the managed ones.
 */
public interface NodeManager extends NodeProvider {

    /**
     * Adds the given node to the managed nodes.
     * 
     * @param node
     *            The node to add.
     * @return The previous value associated to the node or <code>null</code> if the node was not present in the list.
     * @throws IllegalArgumentException
     *             If the <code>node</code> argument is <code>null</code>.
     */
    Node addNode(Node node);

}
