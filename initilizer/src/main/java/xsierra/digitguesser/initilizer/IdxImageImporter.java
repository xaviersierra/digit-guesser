package xsierra.digitguesser.initilizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class IdxImageImporter implements ImageImporter {
	
	private static String IMAGE_FILENAME = "train-images-idx3-ubyte.gz";


	@PostConstruct
	public void importImages() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		
		
		try (InputStream rawStream = classloader.getResourceAsStream(IMAGE_FILENAME);
			GZIPInputStream inImage = new GZIPInputStream(rawStream)){
			
			int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfRows  = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());

            BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
            int numberOfPixels = numberOfRows * numberOfColumns;
            int[] imgPixels = new int[numberOfPixels];

            for(int i = 0; i < 1; i++) {

                if(i % 100 == 0) {System.out.println("Number of images extracted: " + i);}

                for(int p = 0; p < numberOfPixels; p++) {
                    int gray = 255 - inImage.read();
                    imgPixels[p] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                }

                image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);

                
                File outputfile = new File("image.png");

                ImageIO.write(image, "png", outputfile);
                
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
