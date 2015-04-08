package image_creation_testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image_Modification {

	public static void main(String[] args) {
//		System.out.println(System.getProperty("user.dir")); // Current Directory
		File imageFile = new File("Test-Images/test1.jpg"); //System.getProperty("user.dir") + "/Test-Images/test1_changeable.jpg" = absolute path
		
		try {
			BufferedImage image = ImageIO.read(imageFile);
			Graphics2D gr = image.createGraphics();
			
			gr.setColor(Color.BLACK);
			gr.setFont(new Font("SansSerif", Font.BOLD, 16));
			gr.drawString("IT WORKED!!!!", 100, 100);
			gr.drawOval(image.getWidth() / 2, image.getHeight() / 2, 10, 10);
			gr.drawLine(200, 200, 100, 100);
			gr.dispose();
			
			System.out.println("Done with drawing");
			ImageIO.write(image, "jpg", new File("Test-Images/test1_changed.jpg"));
			System.out.println("Saved Image");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
