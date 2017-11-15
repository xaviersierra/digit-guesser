package xsierra.digitguesser.initializer.idx;

import java.io.IOException;
import java.io.InputStream;

public class IdxImageHeader {
	
	private int magicNumberImages; 
	private int numberOfImages; 
	private int numberOfRows;  
	private int numberOfColumns;

	public int getMagicNumberImages() {
		return magicNumberImages;
	}
	public void setMagicNumberImages(int magicNumberImages) {
		this.magicNumberImages = magicNumberImages;
	}
	public int getNumberOfImages() {
		return numberOfImages;
	}
	public void setNumberOfImages(int numberOfImages) {
		this.numberOfImages = numberOfImages;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
    
	public static IdxImageHeader readHeader(InputStream is) throws IOException{
		IdxImageHeader header = new IdxImageHeader();
		header.magicNumberImages = (is.read() << 24) | (is.read() << 16) | (is.read() << 8) | (is.read());
		header.numberOfImages = (is.read() << 24) | (is.read() << 16) | (is.read() << 8) | (is.read());
		header.numberOfRows  = (is.read() << 24) | (is.read() << 16) | (is.read() << 8) | (is.read());
		header.numberOfColumns = (is.read() << 24) | (is.read() << 16) | (is.read() << 8) | (is.read());
		return header;
	}
}
