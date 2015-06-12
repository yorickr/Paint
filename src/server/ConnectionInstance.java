package server;

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
                if( input instanceof String )
                {
                    System.out.println("Sending back PID");
                    Integer outputInt = new Integer(server.getNewPlayerID());
                    sendObject(outputInt);

                }
                if( input instanceof PositionPacket )
                {
                    System.out.println((( PositionPacket ) input).toString());
                    PositionPacket pp = ( PositionPacket ) input;
                    PositionPacket toSend = new PositionPacket();
                    pp.sender.move();
                    toSend.addPosition(pp.sender);
                    server.sendToAllClients(toSend);
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

}
