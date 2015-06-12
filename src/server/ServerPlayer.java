package server;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Created by imegumii on 6/8/15.
 */
public class ServerPlayer implements Serializable
{

    final int SPEED = 100;

    double deltatime;

    boolean movingRight;
    boolean movingLeft;
    boolean movingUp;
    boolean movingDown;

    int PID;

    Point2D position;

    public ServerPlayer(double deltatime, boolean movingRight, boolean movingLeft, boolean movingUp, boolean movingDown, Point2D position , int PID)
    {
        this.movingDown = movingDown;
        this.movingUp = movingUp;
        this.movingRight = movingRight;
        this.movingLeft = movingLeft;
        this.position = position;
        this.deltatime = deltatime;
        this.PID = PID;
    }

    public void move()
    {
        if( movingLeft )
        {
            updatePosition( -SPEED * deltatime, 0 );
        }
        if( movingDown )
        {
            updatePosition( 0, SPEED * deltatime );
        }
        if( movingRight )
        {
            updatePosition( SPEED * deltatime, 0 );
        }
        if( movingUp )
        {
            updatePosition( 0, -SPEED * deltatime );
        }
    }

    public int getPID()
    {
        return PID;
    }

    public void updatePosition( double x, double y )
    {
        this.position = new Point2D.Double( position.getX() + x, position.getY() + y );
    }

}
