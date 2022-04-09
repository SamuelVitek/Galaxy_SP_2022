import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

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

		LoadData ld = new LoadData("negative.csv");
		List<Planet> planets = ld.getPlanets();

		double scale;
		Ellipse2D planetDraw;

		for (Planet planet : planets) {
			planetDraw = new Ellipse2D.Double(planet.getxPosition(), planet.getyPosition(), planet.getR(), planet.getR());

			g2.setColor(Color.BLUE);
			g2.fill(planetDraw);
		}
	}

}
