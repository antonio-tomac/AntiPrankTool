package antiprank;

import java.awt.Color;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class AntiStatus {

	private static final Color SHADE_COLOR = new Color(0, 0, 0, 100);
	private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

	public static void showException(Exception exception) {
		JOptionPane.showMessageDialog(
				null, exception.getClass().getSimpleName() + ": "
				+ exception.getMessage(),
				"Error", JOptionPane.ERROR_MESSAGE
		);
	}

	private static void start(Color backgroundColor) {
		JFrame transparentFrame = new JFrame();
		transparentFrame.setUndecorated(true);
		transparentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		transparentFrame.setBackground(backgroundColor);
		transparentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFrame imageFrame = new JFrame();
		imageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		DrawPanel drawPanel = new DrawPanel();
		imageFrame.add(drawPanel);
		imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CameraShooter cameraShooter = CameraShooter.isCameraSupported()
				? CameraShooter.getCameraShooter() : null;
		Controller controller = new Controller(
				transparentFrame, imageFrame,
				drawPanel, cameraShooter
		);
		transparentFrame.addKeyListener(controller);
		transparentFrame.addMouseListener(controller);
		imageFrame.addKeyListener(controller);
		imageFrame.addMouseListener(controller);

		controller.waiting();
	}

	public static void main(String[] args) {
		try {
			Color backgroundColor = Arrays.asList(args).contains("shade")
					? SHADE_COLOR : TRANSPARENT_COLOR;
			start(backgroundColor);
		} catch (Exception ex) {
			showException(ex);
		}
	}

}
