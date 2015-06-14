package server;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by imegumii on 6/8/15.
 */
public class PositionPacket<T> implements Serializable, Comparable<ArrayList<?>>
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

    @Override public int compareTo(ArrayList<?> objects)
    {
        if( objects.size() > list.size() )
        {
            return -1;
        }
        if( objects.size() < list.size() )
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
