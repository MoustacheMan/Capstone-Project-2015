package image_creation_testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image_Modification {

	public static void main(String[] args) {
//		System.out.println(System.getProperty("user.dir")); // Current Directory
		File imageFile = new File("Test-Images/test1.jpg"); //System.getProperty("user.dir") + "/Test-Images/test1_changeable.jpg" = absolute path
		
		try {
			BufferedImage image = ImageIO.read(imageFile);
			int midWidth = image.getWidth() / 2;
			int midHeight = image.getHeight();
			
			
			WritableRaster wra = image.copyData(null);
			int numPixles = wra.getPixels(midWidth, midHeight, 10, 10, (int[])null).length;
			int[] newPix = new int[numPixles];
			for (int i = 0; i < newPix.length; i++) {
				newPix[i] = 0;
				
			}
			wra.setPixels(midWidth, midHeight, 10, 10, newPix);
			System.out.println("Copied data dimensions " + wra.getWidth() + "x" + wra.getHeight());
			
			ColorModel cModel = image.getColorModel();
			System.out.println("Pixel Size: " + cModel.getPixelSize());
			Raster ra = image.getData();
			int[] middlePixels = ra.getPixels(image.getWidth() / 2, image.getHeight() / 2, 10, 10, (int[])null);
			int [] middlePixel = ra.getPixel(image.getWidth() / 2, image.getHeight() / 2, (int[])null);
			
			for (int i : middlePixel) {
				System.out.println(i);
			}
			System.out.println("Printing rectangle of pixels");
//			System.out.println(middlePixels[0]);
			int count = 0;
//			for (int i : middlePixels) {
//				System.out.println(i);
//				count++;
//			}
			System.out.println("Count: " + count);
			
			
			
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
