import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Třída pro spuštění celé simulace
 *
 * @author Samuel Vítek
 */
public class Galaxy_SP2022 {
	/** Atribut odkazující na instanci timeru */
	private static Timer timer;
	/** Kontrola, jestli je timer aktuálně zastaven */
	private static boolean isStopped = false;
	/** Kontrola, jestli byl timer zastaven v minulém běhu */
	private static boolean wasStopped;
	static JFrame graph = new JFrame();
	static ChartPanel chartPanel;

	/**
	 * Hlavní spouštěcí metoda pro spuštění animace a její automatické překreslování
	 *
	 * @param args parametrem se předává cesta k souboru s daty
	 */
	public static void main(String[] args) {
		JFrame okno = new JFrame();

		okno.setTitle("Galaxy Seminární Práce - Samuel Vítek, A21B0315P");
		okno.setSize(640, 480);

		DrawingPanel panel = new DrawingPanel(args[0]);
		//DrawingPanel panel = new DrawingPanel("data/collision.csv");
		okno.add(panel);

		okno.pack();

		chartPanel = new ChartPanel(panel.makeLineChart());

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);

		int timerPeriod = 1;

		//Nastavení timeru a vytvoření actionListeneru
		timer = new Timer(timerPeriod, new ActionListener() {
			/** Počáteční čas */
			long startTime = System.currentTimeMillis();
			/** Konečný čas */
			long endTime;
			/** Celkový čas */
			double time;
			/** Reference na třídu Galexy_SP2022 */
			final Galaxy_SP2022 g = new Galaxy_SP2022();

			/**
			 * Přepsaná metoda pro překreselní panelu
			 * @param e action event
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				endTime = System.currentTimeMillis();
				if (g.getWasStopped()) {
					endTime = startTime;
				}

				time = (double) (endTime - startTime) / 1000.0 * panel.getTimeStep();
				panel.setTime(time);
				panel.update(time);

				startTime = System.currentTimeMillis();
				panel.repaint();
				chartPanel.setChart(panel.makeLineChart());

				setWasStopped(false);
			}
		});
		timer.start();

		//Nastavení timeru a vytvoření actionListeneru
		Timer secTimer = new Timer(1000, new ActionListener() {
			int sec = 1;

			/**
			 * Přepsaná metoda pro překreselní panelu
			 * @param e action event
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setSpeedAndTime(sec);
				sec++;
			}
		});
		secTimer.start();

		//Zachycení stisku mezerníku a zavolání zastavení animace
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(a -> {
					if (a.getID() == KeyEvent.KEY_PRESSED && a.getKeyCode() == KeyEvent.VK_SPACE) {
						if (!isStopped) {
							stopAnimation();
						} else {
							startAnimation();
						}
					}
					return false;
				});

		//Zachycení akce myší a zavolání příslušné metody nad panelem
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				panel.isPlanetHit(e.getX(), e.getY());
				createNewWindow(panel);
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
	}

	/**
	 * Spuštění animace
	 */
	private static void startAnimation() {
		timer.start();
		isStopped = false;
	}

	/**
	 * Nastavení, jestli byla animace zastavena od minulého vykreslení
	 *
	 * @param s hodnota true/false
	 */
	public static void setWasStopped(boolean s) {
		wasStopped = s;
	}

	/**
	 * Vrátí hodnotu, jestli byla animace zastavena od minulého vykreslení
	 *
	 * @return hodnota true/false
	 */
	public boolean getWasStopped() {
		return wasStopped;
	}

	/**
	 * Zastavení animace
	 */
	public static void stopAnimation() {
		timer.stop();
		isStopped = true;
		setWasStopped(true);
	}

	public static void createNewWindow(DrawingPanel panel) {
		graph.setTitle("Graf");
		graph.setSize(400, 300);

		chartPanel = new ChartPanel(panel.makeLineChart());

		graph.add(chartPanel);

		graph.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				panel.setClickedPlanetToNull();
				graph.dispose();
			}
		});
		graph.setLocationRelativeTo(null);
		graph.setVisible(true);
	}
}
