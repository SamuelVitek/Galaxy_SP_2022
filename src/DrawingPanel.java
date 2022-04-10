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

		LoadData ld = new LoadData("random500.csv");
		List<Planet> planets = ld.getPlanets();

		double spaceWidth = Math.abs(ld.getSpaceEndX()) + Math.abs(ld.getSpaceStartX());
		double spaceHeight = Math.abs(ld.getSpaceEndY()) + Math.abs(ld.getSpaceStartY());

		double scaleX = this.getWidth() / spaceWidth;
		double scaleY = this.getHeight() / spaceHeight;
		double scale = Math.min(scaleX, scaleY);

		Ellipse2D planetDraw;

		for (Planet planet : planets) {

			double scaledR = planet.getR() * scale;

			double scaledPosX = (planet.getxPosition() * scale) - scaledR / 2;
			double scaledPosY = (planet.getyPosition() * scale) - scaledR / 2;

			double startX = (this.getWidth()  / 2.0) + scaledPosX;
			double startY = (this.getHeight() / 2.0) + scaledPosY;

			planetDraw = new Ellipse2D.Double(
					startX,
					startY,
					scaledR,
					scaledR);

			g2.setColor(Color.BLUE);
			g2.fill(planetDraw);
		}



	}

}
