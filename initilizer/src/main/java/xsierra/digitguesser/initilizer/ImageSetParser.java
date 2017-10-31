package xsierra.digitguesser.initilizer;

import java.util.List;

import xsierra.digitguesser.initilizer.domain.Sample;

public interface ImageSetParser {

	List<Sample> readTestSet();

	List<Sample> readTrainSet();
	
}
