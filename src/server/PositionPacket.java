package server;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by imegumii on 6/8/15.
 */
public class PositionPacket implements Serializable
{
    ServerPlayer sender;
    Point2D senderPosition;

    public PositionPacket()
    {
    }

    public void addPosition( ServerPlayer p )
    {
        sender = p;
        senderPosition = p.position;
    }

    public Point2D getSenderPosition()
    {
        return senderPosition;
    }

    public ServerPlayer getSender()
    {
        return sender;
    }

}
