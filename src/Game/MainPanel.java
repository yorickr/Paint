package Game;

import server.PositionPacket;

import javax.swing.*;
import javax.swing.text.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
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
    boolean draw = false;
    Timer gfxTimer;
    Timer serverTimer;
    
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

        serverTimer = new Timer(1000/30, e ->
        {
            sendObject(toDraw);
        });
        serverTimer.start();

        addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				draw = false;
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				draw = true;
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if(draw)
				{
					toDraw.add(new Ellipse2D.Double(e.getPoint().x,e.getPoint().y, 10,10));
				}
			}
		});
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
