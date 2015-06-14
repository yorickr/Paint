package Game;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ColorPanel extends JPanel {
	
	JButton blackButton, redButton,greenButton,magentaButton,blueButton;
	MainPanel panel;
	
	public ColorPanel(MainPanel panel)
	{
		this.panel = panel;
		
		
		ActionListener actionlistener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == blackButton)
				{
					panel.setCurrentColor(Color.black);
				}
				else if(e.getSource() == redButton)
				{
					panel.setCurrentColor(Color.red);
				}
				else if(e.getSource() == greenButton)
				{
					panel.setCurrentColor(Color.green);
				}
				else if(e.getSource() == magentaButton)
				{
					panel.setCurrentColor(Color.magenta);
				}
				else if(e.getSource() == blueButton)
				{
					panel.setCurrentColor(Color.blue);
				}
				
			}
		};
		
		blackButton = new JButton("black");
		blackButton.addActionListener(actionlistener);
		redButton = new JButton("Red");
		redButton.addActionListener(actionlistener);
		greenButton = new JButton("Green");
		greenButton.addActionListener(actionlistener);
		magentaButton = new JButton("Magenta");
		magentaButton.addActionListener(actionlistener);
		blueButton = new JButton("Blue");
		blueButton.addActionListener(actionlistener);
		
		
		add(blackButton);
		add(blueButton);
		add(greenButton);
		add(magentaButton);
		add(redButton);
	}
}
