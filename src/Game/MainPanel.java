package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by imegumii on 6/3/15.
 */
public class MainPanel extends JPanel implements ActionListener
{
    ArrayList<Draw> toDraw;


    MainFrame mainFrame;

    Timer gfxTimer;
    Timer physxTimer;

    double lastTime = System.currentTimeMillis();


    public MainPanel(MainFrame mainFrame)
    {
        super();
        toDraw = new ArrayList<Draw>();
        this.mainFrame = mainFrame;
        gfxTimer = new Timer(1000 / 30, this);
        gfxTimer.start();

        physxTimer = new Timer(1000 / 60, e -> {
            this.update();
        });
        physxTimer.start();

    }

    public void addToToDraw(Draw d)
    {
        toDraw.add(d);
    }

    public void removeFromDraw(Draw d)
    {
        toDraw.remove(d);
    }

    @Override protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D g2d = ( Graphics2D ) graphics;

        toDraw.forEach(e -> {
            e.draw(g2d);
            e.drawCollision(g2d);
        });


    }

    public void update()
    {
        double delta = (System.currentTimeMillis() - lastTime) / 1000;


        lastTime = System.currentTimeMillis();
    }

    @Override public void actionPerformed(ActionEvent actionEvent)
    {
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
