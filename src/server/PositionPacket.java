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
    ArrayList<Shape> list;

    public PositionPacket()
    {
        list = new ArrayList<>();
    }

    public void setList(ArrayList<Shape> list)
    {
        this.list = list;
    }

    public ArrayList<Shape> getList()
    {
        return list;
    }
}
