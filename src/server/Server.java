package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by imegumii on 6/8/15.
 */
public class Server
{
    int portNumber = 33333;

    ArrayList<ConnectionInstance> clients;

    public Server()
    {
        clients = new ArrayList<ConnectionInstance>();
        ServerSocket serverSocket;
        try
        {
            serverSocket = new ServerSocket( portNumber );
            while( true )
            {
                ConnectionInstance ci = new ConnectionInstance( serverSocket.accept(), this );
                ci.start();
                clients.add( ci );
                System.out.println( "Accepted new connection" );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

    }

    public Integer getNewPlayerID()
    {
        if( clients.size() == 0 )
        {
            return new Integer(0);
        }
        return new Integer(clients.size());
    }

    public void sendToAllClients(Object object)
    {
        clients.forEach( c->
        {
            c.sendObject( object );
        });
    }

    public static void main( String[] args )
    {
        new Server();
    }
}
