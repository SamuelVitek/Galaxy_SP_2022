import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Třída DrawinPanel vytváří panel nesoucí všechny infomrace
 * načtené planety, výpočty pro překreslení nebo kontrolu, jestli byla hvězda zasažena myší
 *
 * @author Samuel Vítek
 */
public class DrawingPanel extends JPanel {

	/** Atribut odkazující na načtená data */
	private final LoadData loadedData;
	/** Kolekce nesoucí informace o všech načtenýc planetách */
	private final List<Planet> planets;
	/** Kolekce nesoucí informace o vykreslených objektech, představujících planety */
	private List<Ellipse2D> drawnPlanets;
	/** Atribut pro rozlišení planety, která je aktuálně zakliknuta */
	private Planet clickedPlanet = null;
	/** Atributy ukládající začátek a konec vesmíru */
	private double spaceStartX, spaceEndX;
	private double spaceStartY, spaceEndY;
	/** Atribut ukládající aktuální čas běhu */
	private double time;

	/**
	 * Konstruktor třídy DrawingPanel
	 *
	 * @param path předaná cesta k souboru s daty
	 */
	public DrawingPanel(String path) {
		this.setPreferredSize(new Dimension(800, 600));
		this.loadedData = new LoadData(path);
		this.planets = loadedData.getPlanets();
	}

	/**
	 * Přepsaná metoda pro vykreslení panelu
	 *
	 * @param g grafické rozhraní
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;
		drawnPlanets = new ArrayList<>();

		spaceXY();

		//Výpočty scale a pozicování planet podle středu okna
		double spaceWidth = spaceEndX - spaceStartX;
		double spaceHeight = spaceEndY - spaceStartY;

		double centerX = (spaceEndX + spaceStartX) / 2;
		double centerY = (spaceEndY + spaceStartY) / 2;

		double scaleX = this.getWidth() / spaceWidth;
		double scaleY = this.getHeight() / spaceHeight;
		double scale = Math.min(scaleX, scaleY);

		Ellipse2D planetDraw;

		//Vykreslení jednotlivých planet
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

			String text = "Time: " + time + "s ";
			String name = "Name: " + planet.getName() + " ";
			String position = "Position [" + planet.getxPosition() + ";" + planet.getyPosition() + "] ";
			String speed = "Speed [" + planet.getxSpeed() + ";" + planet.getySpeed() + "] ";

			g2.setColor(Color.BLACK);
			int stringWidth;
			g2.setFont(new Font("Arial", Font.PLAIN, 18));

			//Kontrola jestli je zakliknutá jedna z planet, nastavení barvy a vypsání informací
			if (planet.equals(clickedPlanet)) {
				g2.setColor(Color.BLACK);
				stringWidth = g2.getFontMetrics().stringWidth(name);
				g2.drawString(name, this.getWidth() - stringWidth, 36);

				stringWidth = g2.getFontMetrics().stringWidth(position);
				g2.drawString(position, this.getWidth() - stringWidth, 54);

				stringWidth = g2.getFontMetrics().stringWidth(speed);
				g2.drawString(speed, this.getWidth() - stringWidth, 72);
			} else {
				g2.setColor(Color.BLUE);
			}

			g2.fill(planetDraw);

			g2.setColor(Color.BLACK);
			stringWidth = g2.getFontMetrics().stringWidth(text);
			g2.drawString(text, this.getWidth() - stringWidth, 18);
		}
	}

	/**
	 * Metoda pro aktualizaci stavu vesmíru, výpočet nových souřadnic
	 *
	 * @param t čas od posledního zavolaní této metody
	 */
	public void update(double t) {
		double dt_min = loadedData.getTimeStep() / 10000;

		while (t > 0) {
			double dt = Math.min(t, dt_min);

			for (Planet planet : planets) {
				double aix = computeAcceleration(planet)[0];
				double aiy = computeAcceleration(planet)[1];

				//souradnice rychlosti a pozic a jejich vypocty
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

	/**
	 * Metoda pro výpočet vektoru zrychlení pomocí vzorce N-objektů
	 *
	 * @param i planeta, pro kterou se má výpočet provést
	 * @return vektor zrychlení v poli
	 */
	public double[] computeAcceleration(Planet i) {
		List<Planet> newPlanets = new ArrayList<>(planets);

		newPlanets.remove(i);

		double accelerationX = 0;
		double accelerationY = 0;

		for (Planet j : newPlanets) {
			double px = j.getxPosition() - i.getxPosition();
			double py = j.getyPosition() - i.getyPosition();
			double denominator = Math.sqrt((px * px) + (py * py));
			accelerationX += loadedData.getG() * j.getWeight()	* (px / Math.pow(denominator, 3.0));
			accelerationY += loadedData.getG() * j.getWeight()	* (py / Math.pow(denominator, 3.0));
		}

		return new double[]{accelerationX, accelerationY};
	}

	/**
	 * Getter pro předání časového kroku z podkladového souboru
	 *
	 * @return časový krok za 1s
	 */
	public double getTimeStep() {
		return loadedData.getTimeStep();
	}

	/**
	 * Metoda pro přepočítání velikosti vesmíru
	 * použitá v metodě paint
	 */
	public void spaceXY() {
		spaceStartX = Double.MAX_VALUE;
		spaceEndX = -Double.MAX_VALUE;
		spaceStartY = Double.MAX_VALUE;
		spaceEndY = -Double.MAX_VALUE;

		//Procházení planet a kontorly, které souřadnice jsou nejmenší
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

	/**
	 * Metoda pro zjištění, zda-li byla planeta zasažena myší
	 *
	 * @param x místo, kde byl zaznamenán klik myši na x-ove ose
	 * @param y místo, kde byl zaznamenán klik myši na y-ove ose
	 */
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

	/**
	 * Metoda pro nastavení aktuálního uběhlého času
	 *
	 * @param time uběhlý čas doposud
	 */
	public void setTime(double time) {
		this.time += time;
	}
}
