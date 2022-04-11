import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Galaxy_SP2022 {
	private static Timer timer;
	private static boolean isStopped = false;
	private static boolean wasStopped;

	public static void main(String[] args) {
		JFrame okno = new JFrame();

		okno.setTitle("Galaxy Seminární Práce - Samuel Vítek, A21B0315P");
		okno.setSize(640, 480);

		//DrawingPanel panel = new DrawingPanel(args[0]);
		DrawingPanel panel = new DrawingPanel("data/pulsar.csv");
		okno.add(panel);

		okno.pack();

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);

		int timerPeriod = (int) panel.getTimeStep();

		timer = new Timer(timerPeriod, new ActionListener() {
			long startTime = System.currentTimeMillis();
			long endTime;
			double time;
			final Galaxy_SP2022 g = new Galaxy_SP2022();

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

				setWasStopped(false);
			}
		});
		timer.start();

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

		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				panel.isPlanetHit(e.getX(), e.getY());
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

	private static void startAnimation() {
		timer.start();
		isStopped = false;
	}
	public static void setWasStopped(boolean s) {
		wasStopped = s;
	}
	public boolean getWasStopped() {
		return wasStopped;
	}
	public static void stopAnimation() {
		timer.stop();
		isStopped = true;
		setWasStopped(true);
	}
}
