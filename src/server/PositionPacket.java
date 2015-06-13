package server;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by imegumii on 6/8/15.
 */
public class PositionPacket<T> implements Serializable
{
    static final long serialVersionUID = 42L;
    ArrayList<T> list;

    public PositionPacket()
    {
        list = new ArrayList<>();
    }

    public ArrayList<T> getList()
    {
        return list;
    }

    public void setList(ArrayList<T> list)
    {
        this.list = list;
    }
}
