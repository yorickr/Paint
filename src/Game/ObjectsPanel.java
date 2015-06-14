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
import javax.swing.JSlider;
import javax.xml.stream.events.EndDocument;

public class ObjectsPanel extends JPanel {

	JButton rectangleButton, CircleButton, triangleButton, penButton, ClearScreen,gum;
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
					main.setDrawCircle(false);
					main.setDrawPen(false);
					main.setDrawTriangle(false);
					main.setGum(false);
				} else if (e.getSource() == CircleButton) {
					main.setDrawCircle(true);
					main.setDrawRect(false);
					main.setDrawTriangle(false);
					main.setDrawPen(false);
					main.setGum(false);
				} else if (e.getSource() == triangleButton) {
					main.setDrawTriangle(true);
					main.setDrawCircle(false);
					main.setDrawRect(false);
					main.setDrawPen(false);
					main.setGum(false);
				} else if (e.getSource() == penButton) {
					main.setDrawPen(true);
					main.setDrawCircle(false);
					main.setDrawRect(false);
					main.setDrawTriangle(false);
					main.setGum(false);
				}else if(e.getSource() == ClearScreen)
				{
					main.setClear();
				}else if(e.getSource() == gum)
				{
					main.setGum(true);
					main.setDrawCircle(false);
					main.setDrawRect(false);
					main.setDrawTriangle(false);
					main.setDrawPen(false);
				}
			}
		};

		rectangleButton = new JButton("Rectangle");
		rectangleButton.addActionListener(actionlistener);
		CircleButton = new JButton("Circle");
		CircleButton.addActionListener(actionlistener);
		triangleButton = new JButton("Triangle");
		triangleButton.addActionListener(actionlistener);
		penButton = new JButton("Pen");
		penButton.addActionListener(actionlistener);
		ClearScreen = new JButton("ClearAll");
		ClearScreen.addActionListener(actionlistener);
		gum = new JButton("Gum");
		gum.addActionListener(actionlistener);
		
		add(gum);
		add(ClearScreen);
		add(penButton);
		add(rectangleButton);
		add(triangleButton);
		add(CircleButton);
	}
}
