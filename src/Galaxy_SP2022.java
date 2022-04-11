import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Galaxy_SP2022 {

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

		Timer timer;
		int timerPeriod = (int) panel.getTimeStep();
		timer = new Timer(timerPeriod, new ActionListener() {
			long startTime = System.currentTimeMillis();

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.update((double) (System.currentTimeMillis() - startTime) / 1000.0 * panel.getTimeStep());
				startTime = System.currentTimeMillis();
				panel.repaint();
			}
		});
		timer.start();
	}
}
