package antiprank;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class CameraShooter {

	private static final Webcam webcam = Webcam.getDefault();
	private static final CameraShooter instance = new CameraShooter();	
	
	private CameraShooter() {
		if (webcam != null) {
			webcam.setViewSize(new Dimension(640, 480));
			webcam.open();
		}
	}

	public BufferedImage takePicture() {
		return webcam.getImage();
	}
	
	public static boolean isCameraSupported() {
		return webcam != null;
	}

	public static CameraShooter getCameraShooter() {
		if (isCameraSupported()) {
			return instance;
		}
		throw new RuntimeException("Web cam is not found");
	}
}
