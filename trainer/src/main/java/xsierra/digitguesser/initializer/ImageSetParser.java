package xsierra.digitguesser.initializer;

import xsierra.digitguesser.initializer.domain.Sample;

import java.util.List;

public interface ImageSetParser {

	List<Sample> readTestSet();

	List<Sample> readTrainSet();
	
}
