package Game;

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
        this.add(main);
        this.setVisible( true );
    }
}
