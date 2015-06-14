package Game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

/**
 * Created by imegumii on 6/3/15.
 */
public class MainFrame extends JFrame
{
    public MainFrame()
    {
        super( "Bomberman" );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setSize(900,700);
        MainPanel main = new MainPanel(this);
        ColorPanel colorPanel= new ColorPanel(main);
        ObjectsPanel objPanel = new ObjectsPanel(main);
        this.add(objPanel,BorderLayout.SOUTH);
        this.add(colorPanel, BorderLayout.NORTH);
        this.add(main,BorderLayout.CENTER);
        this.setVisible( true );
    }
}
