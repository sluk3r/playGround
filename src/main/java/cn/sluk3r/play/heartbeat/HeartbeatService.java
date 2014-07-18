package cn.sluk3r.play.heartbeat;

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

/**
 * Service interface to manage heartbeat messaging.
 * 
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public interface HeartbeatService<M extends Serializable> {

    /**
     * Starts the process that sends and receives the heartbeat messages.
     */
    void start();

    /**
     * Stops the sending and receiving of heartbeat messages.
     */
    void stop();

}
