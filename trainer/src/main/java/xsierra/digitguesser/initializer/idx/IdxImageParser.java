package xsierra.digitguesser.initializer.idx;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import xsierra.digitguesser.initializer.ImageSetParser;
import xsierra.digitguesser.initializer.ImportProperties;
import xsierra.digitguesser.initializer.ImportSamplesException;
import xsierra.digitguesser.initializer.domain.ImageDataSet;
import xsierra.digitguesser.initializer.domain.Sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

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
				sample.setId(i);
				readedSamples.add(sample);

			}

		} catch (IOException e) {
			throw new ImportSamplesException("Cannot read import files", e);
		}

//		ImageDataSet dataSet = new ImageDataSet();
//		for (Sample s: readedSamples) {
//			double[] img = s.getImage();
//			double[] label = {s.getLabel()};
//			dataSet.stack(Nd4j.create(s.getImage()), Nd4j.create(label));
//		}
//
//		INDArray features = Transforms.normalizeZeroMeanAndUnitVariance(dataSet.getFeatures());
//
//		for (int i = 0; i < features.shape()[1]; i++) {
//			INDArray row = features.getRow(i);
//			readedSamples.get(i).setImage(row);
//		}

		return readedSamples;
	}


}
