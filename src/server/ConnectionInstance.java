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

            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

}
