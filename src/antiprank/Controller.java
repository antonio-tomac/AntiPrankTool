package antiprank;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class Controller implements KeyListener, MouseListener {

	private final JFrame transparentFrame;
	private final JFrame imageFrame;
	private final DrawPanel drawPanel;
	private final CameraShooter cameraShooter;

	private final String TAKEN_IMAGES_DIR_NAME = "takenImages";
	private final String FUNNY_MESSAGE = "E nećeš razbojniče";
	private final String WEb_CAM_NOT_FOUND_MESSAGE = "Webcam not found";

	public Controller(JFrame transparentFrame, JFrame imageFrame,
			DrawPanel drawPanel, CameraShooter cameraShooter) {
		this.transparentFrame = transparentFrame;
		this.imageFrame = imageFrame;
		this.drawPanel = drawPanel;
		this.cameraShooter = cameraShooter;
	}

	public void fireImage() {
		stopWaiting();
		if (cameraShooter != null) {
			BufferedImage pictureImage = cameraShooter.takePicture();
			drawPanel.drawImageAndText(pictureImage, FUNNY_MESSAGE);
			saveImage(pictureImage);
		} else {
			drawPanel.drawText(WEb_CAM_NOT_FOUND_MESSAGE);
		}
		imageFrame.repaint();
	}

	public void waiting() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				imageFrame.setVisible(false);
				transparentFrame.setVisible(true);
			}
		});
	}

	private void stopWaiting() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				transparentFrame.setVisible(false);
				imageFrame.setVisible(true);
			}
		});
	}

	private void saveImage(BufferedImage bufferedImage) {
		String date = new Date().toString().replace(' ', '_').replace(':', '-');
		String imageName = "image_" + date + ".jpeg";
		File directory = new File(TAKEN_IMAGES_DIR_NAME);
		if (!directory.exists()) {
			try {
				directory.mkdirs();
			} catch (Exception ex) {
				drawPanel.drawInfoText("exception on dir: " + ex.getMessage());
			}
		}
		try {
			File file = new File(TAKEN_IMAGES_DIR_NAME + File.separator + imageName);
			ImageIO.write(bufferedImage, "JPG", file);
			drawPanel.drawInfoText("saved image to: " + file.getAbsolutePath());
		} catch (IOException ex) {
			drawPanel.drawInfoText("exception on saving: " + ex.getMessage());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			fireImage();
		} catch (Exception exception) {
			AntiStatus.showException(exception);
			throw new RuntimeException(exception);
		}
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ALT:
				case KeyEvent.VK_Q:
					System.exit(0);
					break;
				case KeyEvent.VK_ESCAPE:
					waiting();
					break;
				default:
					fireImage();
			}
		} catch (Exception exception) {
			AntiStatus.showException(exception);
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
