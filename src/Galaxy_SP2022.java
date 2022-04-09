import javax.swing.*;

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
	}
}
