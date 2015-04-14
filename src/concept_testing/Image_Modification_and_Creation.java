package concept_testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Image_Modification_and_Creation {

	// Further look into:
	//	raster.getPixels vs getPixel and the single sample vs all samples (missing information?) 
	//		Edit: Apparently no information is lost when doing getPixels and the single sample for a pixel because, 
	//		as shown in the fox lady test below, the cropped image still renders perfectly fine even though supposedly 
	//		only one sample for each pixel was taken which sounds like only one value of the RGB values used to create the pixel color
	//		--Investigate this further--
	//	Buffered image type
	//	The other ways to create a BufferedImage
	//	Optimization for getting numPix from/for raster
	//	Other optimizations
	
	public static void main(String[] args) {
		Random rand = new Random();
		
//		System.out.println(System.getProperty("user.dir")); // Current Directory
		File imageFile = new File("Test-Images/test1.jpg"); //System.getProperty("user.dir") + "/Test-Images/test1_changeable.jpg" = absolute path
		
		
		// Use static methods in Raster class to properly generate a raster from scratch
//		Raster.someMethod()
		
		
		try {
			BufferedImage image = ImageIO.read(imageFile);
			int midWidth = image.getWidth() / 2;
			int midHeight = image.getHeight() / 2;
			int sampleBoxDimen = 20;
//			System.out.println(image.getType());
			
			// "Cropping" a part of one image to a new image
			File foxFile = new File("Test-Images/Foxy_Lady.jpg");
			BufferedImage foxLady = ImageIO.read(foxFile);
			int foxMidW = foxLady.getWidth() / 2;
			int foxMidH = foxLady.getHeight() / 2;
			Raster foxRa = foxLady.getData();
			int[] imgPix = foxRa.getPixels(foxMidW / 2, foxMidH / 2, foxMidW, foxMidH, (int[])null);
			BufferedImage croppedFox = new BufferedImage(foxMidW, foxMidH, foxLady.getType());
			// Solution 1 to setting pixel data of new img
			WritableRaster cropWra = croppedFox.getRaster();
			cropWra.setPixels(0, 0, foxMidW, foxMidH, imgPix);
			// Solution 2 to setting pixel data of new img
			// creating a new raster using the pixel data then passing it to setData() of the new img
			
			ImageIO.write(croppedFox, "jpg", new File("Test-images/croppedImage.jpg"));
			
			// Resetting pixels at center of image
			WritableRaster wra = image.copyData(null);
			int numPixels = wra.getPixels(midWidth, midHeight, sampleBoxDimen, sampleBoxDimen, (int[])null).length;
			int[] newPix = new int[numPixels];
			for (int i = 0; i < newPix.length; i++) {
				newPix[i] = rand.nextInt(256);
				
			}
			wra.setPixels(midWidth, midHeight, sampleBoxDimen, sampleBoxDimen, newPix);
			System.out.println("Center img rand pix Copied data dimensions " + wra.getWidth() + "x" + wra.getHeight());
			
			BufferedImage newImg = new BufferedImage(wra.getWidth(), wra.getHeight(), image.getType());
			newImg.setData(wra);
			ImageIO.write(newImg, "jpg", new File("Test-Images/test1_center_random.jpg"));
			
			// Resetting pixels of entire image
			WritableRaster wra2 = image.copyData(null);
			int numPixels2 = wra2.getPixels(0, 0, wra2.getWidth(), wra2.getHeight(), (int[])null).length;
			int[] newPix2 = new int[numPixels2];
			for (int i = 0; i < newPix2.length; i++) {
				newPix2[i] = rand.nextInt(256);
				
			}
			wra2.setPixels(0, 0, wra2.getWidth(), wra2.getHeight(), newPix2);
			System.out.println("Entire img rand pix Copied data dimensions " + wra2.getWidth() + "x" + wra2.getHeight());
			
			BufferedImage newImg2 = new BufferedImage(wra2.getWidth(), wra2.getHeight(), image.getType());
			newImg2.setData(wra2);
			ImageIO.write(newImg2, "jpg", new File("Test-Images/test1_pix_random.jpg"));
			
			// Other image stuff
			ColorModel cModel = image.getColorModel();
			System.out.println("Pixel Size: " + cModel.getPixelSize());
			// Reading pixel data
			Raster ra = image.getData();
			int[] middlePixels = ra.getPixels(image.getWidth() / 2, image.getHeight() / 2, 10, 10, (int[])null);
			int[] middlePixel = ra.getPixel(image.getWidth() / 2, image.getHeight() / 2, (int[])null);
			
			System.out.println("Single Pix samples:");
			for (int i : middlePixel) {
				System.out.println(i);
			}
			System.out.println("Printing rectangle of pixels");
			System.out.println(middlePixels[0]);
			int count = 0;
			for (int i : middlePixels) {
				System.out.println(i);
				count++;
			}
			System.out.println("Pix Count in Rectangle: " + count);
			
			// Drawing shapes/text onto existing image
			Graphics2D gr = image.createGraphics();
			
			gr.setColor(Color.BLACK);
			gr.drawOval(image.getWidth() / 2, image.getHeight() / 2, 10, 10);
			gr.setFont(new Font("SansSerif", Font.BOLD, 16));
			gr.setColor(Color.WHITE);
			gr.drawString("IT WORKED!!!!", 100, 100);
			gr.drawLine(100, 110, 200, 110);
			gr.dispose();
			System.out.println("Done with drawing");
			ImageIO.write(image, "jpg", new File("Test-Images/test1_changed.jpg"));
			
			System.out.println("Saved Images");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
