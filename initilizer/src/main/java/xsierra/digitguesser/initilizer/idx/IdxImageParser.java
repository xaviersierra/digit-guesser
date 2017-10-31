package xsierra.digitguesser.initilizer.idx;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import xsierra.digitguesser.initilizer.ImageSetParser;
import xsierra.digitguesser.initilizer.ImportProperties;
import xsierra.digitguesser.initilizer.ImportSamplesException;
import xsierra.digitguesser.initilizer.domain.Sample;

@Component
public class IdxImageParser implements ImageSetParser {

	private ImportProperties properties;

	@Autowired
	public IdxImageParser(ImportProperties properties) {
		super();
		this.properties = properties;
	}
	
	@Override
	public List<Sample> readTrainSet(){
		return importImages(properties.getTrainingImageFile(), properties.getTrainingLabelFile());
	}
	
	@Override
	public List<Sample> readTestSet(){
		return importImages(properties.getTestImageFile(), properties.getTestLabelFile());
	}

	protected List<Sample> importImages(String imageFile, String labelFile) {

		List<Sample> readedSamples = new ArrayList<>();		

		try (InputStream rawImageStream = new ClassPathResource(imageFile).getInputStream();
				GZIPInputStream inImage = new GZIPInputStream(rawImageStream);
				InputStream rawLabelStream = new ClassPathResource(labelFile).getInputStream();
				GZIPInputStream inLabel = new GZIPInputStream(rawLabelStream)) {

			IdxImageHeader imageHeader = IdxImageHeader.readHeader(inImage);

			IdxLabelHeader.readHeader(inLabel);

			int numberOfPixels = imageHeader.getNumberOfRows() * imageHeader.getNumberOfColumns();

			for (int i = 0; i < imageHeader.getNumberOfImages(); i++) {

				Sample sample = Sample.readSample(inImage, inLabel, numberOfPixels);
				readedSamples.add(sample);

			}

		} catch (IOException e) {
			throw new ImportSamplesException("Cannot read import files", e);
		}

		return readedSamples;
	}


}
