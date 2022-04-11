import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	private final LoadData ld = new LoadData("data/collision.csv");
	private final List<Planet> planets = ld.getPlanets();
	private List<Ellipse2D> drawnPlanets;
	private double spaceStartX;
	private double spaceEndX;
	private double spaceStartY;
	private double spaceEndY;
	private Planet clickedPlanet = null;
	private double time;

	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;
		drawnPlanets = new ArrayList<>();

		spaceXY();

		double spaceWidth = spaceEndX - spaceStartX;
		double spaceHeight = spaceEndY - spaceStartY;

		double centerX = (spaceEndX + spaceStartX) / 2;
		double centerY = (spaceEndY + spaceStartY) / 2;

		//System.out.println("spaceWidth: " + spaceWidth);

		double scaleX = this.getWidth() / spaceWidth;
		double scaleY = this.getHeight() / spaceHeight;
		double scale = Math.min(scaleX, scaleY);

		System.out.println("Scale: " + scale);

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

			drawnPlanets.add(planetDraw);

			if (planet.equals(clickedPlanet)) {
				g2.setColor(Color.RED);
			} else {
				g2.setColor(Color.BLUE);
			}
			g2.fill(planetDraw);
		}

		String text = "Time: " + time + "s ";
		g2.setFont(new Font("Arial", Font.BOLD, 18));
		g2.setColor(Color.BLACK);
		int stringWidth = g2.getFontMetrics().stringWidth(text);
		int stringHeight = 18;
		g2.drawString(text, this.getWidth() - stringWidth, stringHeight);
	}

	public void update(double t) {
		double dt_min = ld.getTimeStep() / 10000;

		while (t > 0) {
			double dt = Math.min(t, dt_min);

			for (Planet planet : planets) {
				double aix = computeAcceleration(planet)[0];
				double aiy = computeAcceleration(planet)[1];

				//x-ove souradnice rychlosti a pozic a jejich vypocty
				double vix = planet.getxSpeed();
				double pix = planet.getxPosition();
				double viy = planet.getySpeed();
				double piy = planet.getyPosition();

				vix += 0.5 * dt * aix;
				viy += 0.5 * dt * aiy;
				planet.setxSpeed(vix);
				planet.setySpeed(viy);

				pix += dt * vix;
				piy += dt * viy;
				planet.setxPosition(pix);
				planet.setyPosition(piy);

				vix += 0.5 * dt * aix;
				viy += 0.5 * dt * aiy;
				planet.setxSpeed(vix);
				planet.setySpeed(viy);
			}
			t -= dt;
		}
	}

	public double[] computeAcceleration(Planet i) {
		List<Planet> newPlanets = new ArrayList<>(planets);

		newPlanets.remove(i);

		double accelerationX = 0;
		double accelerationY = 0;

		for (Planet j : newPlanets) {
			double px = j.getxPosition() - i.getxPosition();
			double py = j.getyPosition() - i.getyPosition();
			double denominator = Math.sqrt((px * px) + (py * py));
			accelerationX += ld.getG() * j.getWeight()	* (px / Math.pow(denominator, 3.0));
			accelerationY += ld.getG() * j.getWeight()	* (py / Math.pow(denominator, 3.0));

			//System.out.println(j.getName() + " : " + accelerationX);
		}

		return new double[]{accelerationX, accelerationY};
	}

	public double getTimeStep() {
		return ld.getTimeStep();
	}

	public void spaceXY() {
		spaceStartX = Double.MAX_VALUE;
		spaceEndX = -Double.MAX_VALUE;
		spaceStartY = Double.MAX_VALUE;
		spaceEndY = -Double.MAX_VALUE;

		for (Planet planet : planets) {
			if ((planet.getxPosition() - planet.getR() / 2) < spaceStartX) {
				spaceStartX = planet.getxPosition() - planet.getR() / 2;
			}
			if ((planet.getxPosition() + planet.getR() / 2) > spaceEndX) {
				spaceEndX = planet.getxPosition() + planet.getR() / 2;
			}
			if ((planet.getyPosition() - planet.getR() / 2) < spaceStartY) {
				spaceStartY = planet.getyPosition() - planet.getR() / 2;
			}
			if ((planet.getyPosition() + planet.getR() / 2) > spaceEndY) {
				spaceEndY = planet.getyPosition() + planet.getR() / 2;
			}
		}
	}

	public void isPlanetHit(double x, double y) {
		for (Ellipse2D i : drawnPlanets) {
			if (i.contains(x, y)) {
				if (clickedPlanet == null) {
					clickedPlanet = planets.get(drawnPlanets.indexOf(i));
				} else {
					clickedPlanet = null;
				}

				this.repaint();
				return;
			}
		}

		clickedPlanet = null;
	}

	public void setTime(double time) {
		this.time += time;
	}
}
