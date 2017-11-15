package xsierra.digitguesser.initializer.domain;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.io.InputStream;

public class Sample {
	
	private int id;

	private int[] image;
	
	private int label;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getImage() {
		return image;
	}

	public void setImage(int[] image) {
		this.image = image;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}
	
	public static Sample readSample(InputStream imageSetInputStream, InputStream labelSetInputStream, int numberOfPixels)
			throws IOException {
		Sample sample = new Sample();

		int[] imgPixels = new int[numberOfPixels];
		for (int p = 0; p < numberOfPixels; p++) {
			int gray = 255 - imageSetInputStream.read();
			//imgPixels[p] = 0xFF000000 | (gray << 16) | (gray << 8) | gray;
			imgPixels[p] = gray;
		}
		sample.setImage(imgPixels);
		int label = labelSetInputStream.read();
		sample.setLabel(label);
		return sample;
	}


}
