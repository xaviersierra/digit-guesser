package xsierra.digitguesser.initializer.domain;

import java.util.List;

public interface SampleDAO {

	void saveTrainSamples(List<Sample> samples);

	void saveTestSamples(List<Sample> samples);

	void saveDevSamples(List<Sample> samples);

	List<Sample> readAllTraningSamples();

	List<Sample> readAllTestSamples();

	List<Sample> readAllDevSamples();

}