import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	private double time;
	private final LoadData ld = new LoadData("collision.csv");
	private final List<Planet> planets = ld.getPlanets();

	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;

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

	public void update(double t) {
		double dt_min = ld.getTimeStep() / 100;

		while (t > 0) {
			double dt = Math.min(t, dt_min);

			for (Planet planet : planets) {
				double aix = computeAcceleration(planet)[0];
				double aiy = computeAcceleration(planet)[1];

				//x-ove souradnice rychlosti a pozic a jejich vypocty
				double vix = planet.getxSpeed();
				double pix = planet.getxPosition();

				vix += 0.5 * dt * aix;
				pix += dt * vix;
				vix += 0.5 * dt * aix;

				planet.setxSpeed(vix);
				planet.setxPosition(pix);

				//y-ove souradnice rychlosti a pozic
				double viy = planet.getySpeed();
				double piy = planet.getyPosition();

				viy += 0.5 * dt * aiy;
				piy += dt * viy;
				viy += 0.5 * dt * aiy;

				planet.setySpeed(viy);
				planet.setyPosition(piy);
			}

			t = t - dt;
		}
	}

	public double[] computeAcceleration(Planet i) {
		List<Planet> newPlanets = planets;
		newPlanets.remove(i);

		double accelerationX = 0;
		double accelerationY = 0;

		for (Planet j : newPlanets) {
			double px = j.getxPosition() - i.getxPosition();
			double py = j.getyPosition() - i.getyPosition();
			double denominator = Math.sqrt((px * px) + (py * py));
			accelerationX += ld.getG() * j.getWeight()	* (px / Math.pow(denominator, 3.0));
			accelerationY += ld.getG() * j.getWeight()	* (py / Math.pow(denominator, 3.0));
		}

		return new double[]{accelerationX, accelerationY};
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return time;
	}

	public double getTimePeriod() {
		return 1000 / ld.getTimeStep();
	}
}
