package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.stream.events.EndDocument;

public class ObjectsPanel extends JPanel {

	JButton rectangleButton, ovalButton, triangleButton;
	MainPanel main;
	Point startPoint, endPoint;
	ArrayList<Shape> shapes = new ArrayList<Shape>();

	public ObjectsPanel(MainPanel main) {
		this.main = main;

		ActionListener actionlistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == rectangleButton) {
					main.setDrawRect(true);
				} else if (e.getSource() == ovalButton) {
					main.setDrawOval(true);
				} /*else if (e.getSource() == triangleButton) {
					main.drawTriangle = true;
			}*/
			}
		};
		
		
		rectangleButton = new JButton("Rectangle");
		rectangleButton.addActionListener(actionlistener);
		ovalButton = new JButton("Oval");
		ovalButton.addActionListener(actionlistener);
		triangleButton = new JButton("Triangle");
		triangleButton.addActionListener(actionlistener);

		add(rectangleButton);
		add(triangleButton);
		add(ovalButton);
	}
}
