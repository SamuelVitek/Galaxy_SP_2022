import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;

		g2.translate(getWidth()/2, getHeight()/2);

		Ellipse2D tryf = new Ellipse2D.Double(0,0,10,10);
		g2.setColor(Color.BLUE);
		g2.fill(tryf);
	}

}
