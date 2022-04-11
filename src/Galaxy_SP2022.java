import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Galaxy_SP2022 {
	private static Timer timer;
	private static boolean isStopped = false;
	private static boolean wasStopped;

	public static void main(String[] args) {
		JFrame okno = new JFrame();

		okno.setTitle("Galaxy Seminární Práce - Samuel Vítek, A21B0315P");
		okno.setSize(640, 480);

		DrawingPanel panel = new DrawingPanel();
		okno.add(panel);

		okno.pack();

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);

		int timerPeriod = (int) panel.getTimeStep();

		timer = new Timer(timerPeriod, new ActionListener() {
			long startTime = System.currentTimeMillis();
			long endTime;
			final Galaxy_SP2022 g = new Galaxy_SP2022();

			@Override
			public void actionPerformed(ActionEvent e) {
				endTime = System.currentTimeMillis();
				if (g.getWasStopped()) {
					endTime = startTime;
				}

				panel.update((double) (endTime - startTime) / 1000.0 * panel.getTimeStep());
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
