package Game;

import server.PositionPacket;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by imegumii on 6/3/15.
 */
public class MainPanel extends JPanel implements ActionListener
{
    ArrayList<Shape> toDraw;

    MainFrame mainFrame;

    Timer gfxTimer;

    String hostName = "www.imegumii.nl";
    int port = 33333;

    ObjectOutput out;
    ObjectInput in;

    Socket s;


    public MainPanel(MainFrame mainFrame)
    {
        super();
        toDraw = new ArrayList<>();
        this.mainFrame = mainFrame;
        gfxTimer = new Timer(1000 / 30, this);
        gfxTimer.start();

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

        new Thread(() -> {
            while( true )
            {
                try
                {
                    Object input;
                    if( (input = in.readObject()) != null )
                    {
                        if( input instanceof PositionPacket )
                        {
                            setToDraw((( PositionPacket ) input).getList());
                        }
                    }
                }
                catch( ClassNotFoundException | IOException e )
                {
                    e.printStackTrace();
                }
            }
        }).start();

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

    @Override protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g2d = ( Graphics2D ) graphics;

        toDraw.forEach(e -> {
            g2d.fill(e);
        });


    }

    public void setToDraw(ArrayList<Shape> toDraw)
    {
        this.toDraw = toDraw;
    }

    @Override public void actionPerformed(ActionEvent actionEvent)
    {
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
