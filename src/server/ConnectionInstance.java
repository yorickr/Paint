package server;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionInstance extends Thread
{
    Socket s;
    Server server;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ConnectionInstance(Socket socket, Server server)
    {
        super("Thread");
        this.s = socket;
        this.server = server;
    }

    public void sendObject(Object object)
    {
        try
        {
            out.writeObject(object);
            out.flush();
        }
        catch( IOException e )
        {
            e.printStackTrace();
            server.removeCI(this);
        }

    }

    @Override public void run()
    {
        try
        {
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            Object input;
            while( (input = in.readObject()) != null )
            {
                System.out.println("Received object");
                if( input instanceof PositionPacket )
                {
                    PositionPacket<Shape> p = (PositionPacket<Shape>) input;
                    System.out.println(p.getList());
                    server.allShapes.addAll(p.getList());
                    server.sendAllShapes();
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
            server.removeCI(this);
        }
    }

}
