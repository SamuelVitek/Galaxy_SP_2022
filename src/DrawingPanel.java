import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	//private double time;

	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;

		LoadData ld = new LoadData("collision.csv");
		List<Planet> planets = ld.getPlanets();

		double spaceWidth = ld.getSpaceEndX() - ld.getSpaceStartX();
		double spaceHeight = ld.getSpaceEndY() - ld.getSpaceStartY();

		double centerX = (ld.getSpaceEndX() + ld.getSpaceStartX()) / 2;
		double centerY = (ld.getSpaceEndY() + ld.getSpaceStartY()) / 2;

		double scaleX = this.getWidth() / spaceWidth;
		double scaleY = this.getHeight() / spaceHeight;
		double scale = Math.min(scaleX, scaleY);

		Ellipse2D planetDraw;

		for (Planet planet : planets) {

			double scaledR = planet.getR() * scale;

			double scaledPosX = ((planet.getxPosition() - centerX)  * scale) - scaledR / 2;
			double scaledPosY = ((planet.getyPosition() - centerY) * scale) - scaledR / 2;

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

//	public void setTime(double time) {
//		this.time = time;
//	}
//
//	public double getTime() {
//		return time;
//	}

}
