package cn.sluk3r.play.heartbeat.jgroups;

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
import cn.sluk3r.play.heartbeat.MessageListener;
import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The implementation of {@link HeartbeatService} that sends and receives heartbeat messages via JGroups.
 * 
 *
 * @param <M>
 *            The type of the message that will be sent in the heartbeat message.
 */
public class JGroupsHeartbeatServiceImpl<M extends Serializable> implements HeartbeatService<M> {

    private final class MessageReceiverAdapter extends ReceiverAdapter {

        @Override
        public void receive(final Message message) {
            Object messageObject = message.getObject();
            if (!customMessage.getClass().isInstance(messageObject)) {
                throw new IllegalStateException("Unsupported message object class ["
                        + messageObject.getClass().getName() + "], should be ["
                        + customMessage.getClass().getName() + "]");
            }
            if (messageListener != null) {
                messageListener.afterMessageReceived(message);
            }

        }
    }

    private final class MessageSendingTask extends TimerTask {

        @Override
        public void run() {
            try {
                if (jChannel == null) {
                    initJChanel();
                }
                jChannel.send(new Message(null, customMessage));
            } catch (Exception e) {
                LOGGER.error("Failed to send heartbeat message. Retry in [" + period + "] ms", e);
            }
        }

    }
    private static final Logger LOGGER = Logger.getLogger(JGroupsHeartbeatServiceImpl.class);


    private JChannel jChannel; //wangxc 接下来的问题， 应该就集中在JChannel的理解上了。 2014-07-18 15:29:02

    private final long period;

    private final M customMessage;

    private final MessageListener messageListener;

    private final String clusterName;

    private final Timer timer = new Timer("heartbeat-timer");

    public JGroupsHeartbeatServiceImpl(final M customMessage, final long period, final String clusterName,
            final MessageListener messageListener) {
        super();
        if (period <= 0) {
            throw new IllegalArgumentException("period must be greater than 0");
        }
        this.period = period;
        this.customMessage = customMessage;
        this.clusterName = clusterName;
        this.messageListener = messageListener;
    }

    private void closeJChanel() {
        if (jChannel != null) {
            jChannel.close();
            jChannel = null;
        }
    }

    private void initJChanel() throws Exception {
        closeJChanel();
        try {
            jChannel = new JChannel();
            jChannel.connect(clusterName);
            jChannel.setReceiver(new MessageReceiverAdapter());
            LOGGER.info("JChanel successfully connected to cluster [" + clusterName + "]");
        } catch (Exception e) {
            closeJChanel();
            throw e;
        }
    }

    @Override
    public void start() {
        try {
            initJChanel();
        } catch (Exception e) {
            LOGGER.error("Failed to initialize JChanle. Retry in [" + period + "] ms", e);
        }
        timer.scheduleAtFixedRate(new MessageSendingTask(), 0, period);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
        closeJChanel();
        LOGGER.info("JChanel successfully disconnected from cluster [" + clusterName + "]");
    }
}
