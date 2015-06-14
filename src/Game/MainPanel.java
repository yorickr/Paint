package Game;

import server.PositionPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by imegumii on 6/3/15.
 */
public class MainPanel extends JPanel implements ActionListener, Comparable
{
    ArrayList<Shape> toDraw;
    ArrayList<Shape> toSend;
    boolean drawRectangle = false;
    boolean drawCircle = false;
    boolean drawLine = false;
    boolean drawPen = false;
    boolean gum = false;

    Color currentColor;
    Point startDrag, endDrag, midPoint;
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
        toSend = new ArrayList<>();
        this.mainFrame = mainFrame;
        setBackground(Color.WHITE);
        gfxTimer = new Timer(1000 / 30, this);
        gfxTimer.start();

        addMouseListener(new MouseListener()
        {

            @Override public void mouseReleased(MouseEvent e)
            {
                if( drawRectangle )
                {
                    toSend.add(drawRectangle(startDrag.x, startDrag.y, e.getX(), e.getY()));
                    startDrag = null;
                    endDrag = null;
                    drawCircle = false;
                    drawPen = false;
                    drawLine = false;
                    gum = false;
                }
                else if( drawCircle )
                {
                    toSend.add(drawCircle(startDrag.x, startDrag.y, e.getX(), e.getY()));
                    startDrag = null;
                    endDrag = null;
                    drawRectangle = false;
                    drawPen = false;
                    drawLine = false;
                    gum = false;
                }
                else if( drawLine )
                {
                    toSend.add(drawLine(startDrag.x, startDrag.y, e.getX(), e.getY()));
                    drawCircle = false;
                    drawRectangle = false;
                    drawPen = false;
                    gum = false;

                }
                else if( drawPen )
                {
                    drawLine = false;
                    drawCircle = false;
                    drawRectangle = false;
                    gum = false;
                }
                else if( gum )
                {
                    drawLine = false;
                    drawCircle = false;
                    drawRectangle = false;
                    drawPen = false;
                }
            }

            @Override public void mousePressed(MouseEvent e)
            {
                if( drawRectangle )
                {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                    drawCircle = false;
                    drawPen = false;
                    drawLine = false;
                    gum = false;
                }
                else if( drawCircle )
                {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                    drawRectangle = false;
                    drawPen = false;
                    drawLine = false;
                    gum = false;
                }
                else if( drawLine )
                {
                    startDrag = new Point(e.getX(), e.getY());
                    drawCircle = false;
                    drawRectangle = false;
                    drawPen = false;
                    gum = false;
                }
                else if( drawPen )
                {
                    drawLine = false;
                    drawCircle = false;
                    drawRectangle = false;
                    gum = false;
                }
                else if( gum )
                {
                    drawCircle = false;
                    drawPen = false;
                    drawLine = false;
                    drawRectangle = false;
                }
            }

            @Override public void mouseExited(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override public void mouseEntered(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override public void mouseClicked(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }
        });
        addMouseMotionListener(new MouseMotionListener()
        {

            @Override public void mouseMoved(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override public void mouseDragged(MouseEvent e)
            {

                if( drawRectangle )
                {
                    endDrag = new Point(e.getX(), e.getY());
                    drawCircle = false;
                    drawPen = false;
                    drawLine = false;
                    gum = false;
                }
                else if( drawCircle )
                {

                    // toSend.add(new Ellipse2D.Double(e.getPoint().x,
                    // e.getPoint().y, 10, 10));
                    endDrag = new Point(e.getX(), e.getY());
                    drawRectangle = false;
                    drawPen = false;
                    drawLine = false;
                    gum = false;
                }
                else if( drawLine )
                {
                    //endDrag = new Point(e.getX(), e.getY());

                    drawCircle = false;
                    drawRectangle = false;
                    drawPen = false;
                    gum = false;

                }
                else if( drawPen )
                {
                    toSend.add(new Ellipse2D.Double(e.getPoint().x, e.getPoint().y, 10, 10));
                    drawLine = false;
                    drawCircle = false;
                    drawRectangle = false;
                    gum = false;

                }
                else if( gum )
                {
                    toSend.add(new Ellipse2D.Double(e.getPoint().x, e.getPoint().y, 10, 10));
                    drawCircle = false;
                    drawPen = false;
                    drawLine = false;
                    drawRectangle = false;
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
                            PositionPacket p = ( PositionPacket ) input;
                            ArrayList<Shape> list = p.getList();
                            list.sort(new Comparator<Shape>() {
                                @Override public int compare(Shape shape, Shape t1)
                                {
                                    if( shape.getBounds().getX() < t1.getBounds().getX() )
                                    {
                                        return -1;
                                    }
                                    if( shape.getBounds().getX() > t1.getBounds().getX() )
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                            });
                            toDraw = list;
                            Thread.sleep(1000 / 10);
                        }
                    }
                }
                catch( ClassNotFoundException | IOException e )
                {
                    e.printStackTrace();
                }
                catch( InterruptedException e )
                {
                    e.printStackTrace();
                }
            }
        }).start();

        serverTimer = new Timer(1000 / 10, e -> {
            PositionPacket p = new PositionPacket();
            p.setList(new ArrayList<Shape>(toSend));
            sendObject(p);
            toSend.clear();
        });
        serverTimer.start();

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

        g2d.setColor(currentColor);

        if( drawCircle )
        {
            new ArrayList<Shape>(toDraw).forEach(e -> {
                g2d.fill(e);
                if( startDrag != null && endDrag != null )
                {
                    Shape c = drawCircle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    g2d.draw(c);
                }
            });
        }
        else if( drawRectangle )
        {
            new ArrayList<Shape>(toDraw).forEach(e -> {
                g2d.fill(e);
                if( startDrag != null && endDrag != null )
                {
                    Shape r = drawRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    g2d.draw(r);
                }
            });
        }
        else if( drawLine )
        {
            new ArrayList<Shape>(toDraw).forEach(e -> {
                g2d.fill(e);
                if( startDrag != null && endDrag != null )
                {
                    Shape l = drawLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                    g2d.draw(l);
                }
            });
        }
        else if( drawPen )
        {
            new ArrayList<Shape>(toDraw).forEach(e -> {
                g2d.fill(e);
            });
        }
        else if( gum )
        {

            new ArrayList<Shape>(toDraw).forEach(e -> {
                g2d.fill(e);
            });
            g2d.setColor(Color.WHITE);

        }


    }

    public Color getColor()
    {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor)
    {
        this.currentColor = currentColor;
    }

    public void setDrawRect(boolean drawRect)
    {
        this.drawRectangle = drawRect;
    }

    public void setDrawCircle(boolean drawCircle)
    {
        this.drawCircle = drawCircle;
    }

    public void setDrawPen(boolean drawPen)
    {
        this.drawPen = drawPen;
    }

    public void setGum(boolean gum)
    {
        this.gum = gum;
    }

    public void setClear()
    {
        toDraw.clear();
    }

    public void setDrawLine(boolean drawLine)
    {
        this.drawLine = drawLine;
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

    private Rectangle2D.Double drawRectangle(int x1, int y1, int x2, int y2)
    {
        return new Rectangle2D.Double(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private Ellipse2D.Double drawCircle(int x1, int y1, int x2, int y2)
    {
        return new Ellipse2D.Double(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private Line2D.Double drawLine(int x1, int y1, int x2, int y2)
    {
        return new Line2D.Double(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    @Override public int compareTo(Object o)
    {

        return 0;
    }
}
