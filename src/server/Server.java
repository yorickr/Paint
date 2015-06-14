package server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by imegumii on 6/8/15.
 */
public class Server
{
    int portNumber = 33333;

    ArrayList<Shape> allShapes;
    ArrayList<ConnectionInstance> clients;

    public Server()
    {
        clients = new ArrayList<ConnectionInstance>();
        allShapes = new ArrayList<>();

        new Thread(() -> {

            while( true )
            {
                System.out.println("Sending all shapes");
                sendAllShapes();
                try
                {
                    Thread.sleep(1000/5);
                }
                catch( InterruptedException e )
                {
                    e.printStackTrace();
                }

            }
        }).start();

        ServerSocket serverSocket;
        try
        {
            serverSocket = new ServerSocket(portNumber);
            while( true )
            {
                ConnectionInstance ci = new ConnectionInstance(serverSocket.accept(), this);
                ci.start();
                clients.add(ci);
                System.out.println("Accepted new connection");
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        new Server();
    }

    public void sendAllShapes()
    {
        PositionPacket<Shape> p = new PositionPacket<>();
        p.setList(new ArrayList<>(allShapes));
        sendToAllClients(p);

    }

    public void removeCI(ConnectionInstance ci)
    {
        this.clients.remove(ci);
    }

    public void sendObjectToAllRecursively(Object o, ArrayList<ConnectionInstance> list)
    {
        if( list.size() == 0 )
        {
            return;
        }
        ConnectionInstance c = list.get(list.size() - 1);
        c.sendObject(o);
        list.remove(c);
        sendObjectToAllRecursively(o, list);

    }

    public void sendToAllClients(Object object)
    {
//        new ArrayList<>(clients).forEach(c -> {
//            c.sendObjectToAllRecursively(object);
//        });
        sendObjectToAllRecursively(object, new ArrayList<>(clients));
    }
}
