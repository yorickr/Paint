package server;

import Game.Player;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.Socket;

/**
 * Created by imegumii on 6/8/15.
 */
public class Client
{
    String hostName = "www.imegumii.nl";
    int port = 33333;

    ObjectOutput out;
    ObjectInput in;

    Socket s;

    public Client()
    {
        try
        {
            s = new Socket( hostName, port );


            out = new ObjectOutputStream( s.getOutputStream() );
            in = new ObjectInputStream( s.getInputStream() );

        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        try
        {
            Player p = new Player( 100, 80, ImageIO.read( new File( "res/player.png" ) ), 0, 1, null );
            p.moveRight();
            p.setDeltatime( 0.016 );
            sendPosition( p.toServerPlayer());
            getPlayerID();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        while( true )
        {
            try
            {
                Object input;
                if( ( input = in.readObject() ) != null )
                {
                    if( input instanceof PositionPacket )
                    {
                        System.out.println( ( ( PositionPacket ) input ).toString() );
                    }
                    if( input instanceof Integer )
                    {
                        System.out.println("Gotten PID: " + input);
                    }
                }
            }
            catch( ClassNotFoundException | IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args )
    {
        new Client();
    }

    public void sendPosition( ServerPlayer p )
    {
        PositionPacket pp = new PositionPacket();
        pp.addPosition( p );
        sendObject(pp);
    }

    public void getPlayerID()
    {
        String s = "getPlayerID";
        sendObject(s);
    }

    public void sendObject(Object o)
    {
        try
        {
            out.writeObject(o);
            out.flush();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
