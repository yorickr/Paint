package server;

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
            s = new Socket(hostName, port);


            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());

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
                if( (input = in.readObject()) != null )
                {

                }
            }
            catch( ClassNotFoundException | IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        new Client();
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
