package xsierra.digitguesser.initilizer;

import java.io.IOException;
import java.io.InputStream;

public class IdxLabelHeader {
	
	private int magicNumberLabels;
    private int numberOfLabels;

	public int getMagicNumberLabels() {
		return magicNumberLabels;
	}

	public void setMagicNumberLabels(int magicNumberLabels) {
		this.magicNumberLabels = magicNumberLabels;
	}

	public int getNumberOfLabels() {
		return numberOfLabels;
	}

	public void setNumberOfLabels(int numberOfLabels) {
		this.numberOfLabels = numberOfLabels;
	}
	
	public static IdxLabelHeader readHeader(InputStream inLabel) throws IOException{
		IdxLabelHeader header = new IdxLabelHeader();
		header.magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
		header.numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
		return header;
	}

}
