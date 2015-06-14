package Game;

import server.PositionPacket;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by imegumii on 6/3/15.
 */
public class MainPanel extends JPanel implements ActionListener {
	ArrayList<Shape> toDraw;
	ArrayList<Shape> toDrawRect;
	ArrayList<Shape> toSend;

	boolean drawRectangle = false;
	boolean drawCircle = false;
	boolean drawTriangle = false;

	LinkedList<Polygon> triangles = new LinkedList<Polygon>();
	Polygon triangle;
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

	public MainPanel(MainFrame mainFrame) {
		super();
		toDraw = new ArrayList<>();
		toDrawRect = new ArrayList<>();
		toSend = new ArrayList<>();

		this.mainFrame = mainFrame;
		setBackground(Color.WHITE);
		gfxTimer = new Timer(1000 / 30, this);
		gfxTimer.start();

		addMouseListener(new MouseListener() {

			@SuppressWarnings("null")
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(drawRectangle || drawCircle)
				{
				toDrawRect.add(drawRectangle(startDrag.x, startDrag.y,	e.getX(), e.getY()));
				startDrag = null;
				endDrag = null;
				toSend.clear();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				if(drawRectangle || drawCircle)
				{
				startDrag = new Point(e.getX(), e.getY());
				endDrag = startDrag;
				}
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

				
				if (drawRectangle || drawCircle) {
					// toSend.add(new Ellipse2D.Double(e.getPoint().x,
					// e.getPoint().y, 10, 10));
					toDraw.add(new Ellipse2D.Double(e.getPoint().x, e
							.getPoint().y, 10, 10));
					endDrag = new Point(e.getX(), e.getY());

				}
			}
		});

		/*try {
			s = new Socket(hostName, port);
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			while (true) {
				try {
					Object input;
					if ((input = in.readObject()) != null) {
						if (input instanceof PositionPacket) {
							PositionPacket p = (PositionPacket) input;
							toDraw = p.getList();
							Thread.sleep(1000 / 10);
						}
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		serverTimer = new Timer(1000 / 10, e -> {
			PositionPacket p = new PositionPacket();
			p.setList(new ArrayList<Shape>(toSend));
			sendObject(p);
		});
		serverTimer.start();
*/
	}

	public void sendObject(Object o) {
		try {
			out.writeObject(o);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setColor(currentColor);

		if (drawCircle) {
			new ArrayList<Shape>(toDraw).forEach(e -> {
				g2d.fill(e);
			});
		}
		else if (drawRectangle) {
			if (startDrag != null && endDrag != null) {
			        Shape r = drawRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
			        g2d.draw(r);
			        g2d.fill(r);
			}
		}
	}

	public Color getColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public void setDrawRect(boolean drawRect) {
		this.drawRectangle = drawRect;
	}

	public void setDrawOval(boolean drawOval) {
		this.drawCircle = drawOval;
	}

	public void setToDraw(ArrayList<Shape> toDraw) {
		this.toDraw = toDraw;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		repaint();
		Toolkit.getDefaultToolkit().sync();
	}

	private Rectangle2D.Double drawRectangle(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Double(Math.min(x1, x2), Math.min(y1, y2),
				Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
}
