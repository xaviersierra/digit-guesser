package xsierra.digitguesser.initilizer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("import")
public class ImportProperties {
	
	private String trainingImageFile;
	
	private String trainingLabelFile; 
	
	private String testImageFile;
	
	private String testLabelFile;
	
	private int maximunImageNumber;

	public String getTrainingImageFile() {
		return trainingImageFile;
	}

	public void setTrainingImageFile(String trainingImageFile) {
		this.trainingImageFile = trainingImageFile;
	}

	public String getTrainingLabelFile() {
		return trainingLabelFile;
	}

	public void setTrainingLabelFile(String trainingLabelFile) {
		this.trainingLabelFile = trainingLabelFile;
	}

	public String getTestImageFile() {
		return testImageFile;
	}

	public void setTestImageFile(String testImageFile) {
		this.testImageFile = testImageFile;
	}

	public String getTestLabelFile() {
		return testLabelFile;
	}

	public void setTestLabelFile(String testLabelFile) {
		this.testLabelFile = testLabelFile;
	}

	public int getMaximunImageNumber() {
		return maximunImageNumber;
	}

	public void setMaximunImageNumber(int maximunImageNumber) {
		this.maximunImageNumber = maximunImageNumber;
	} 
	
	

}
