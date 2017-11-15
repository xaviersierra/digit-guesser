package xsierra.digitguesser.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsierra.digitguesser.initializer.domain.Sample;
import xsierra.digitguesser.initializer.domain.SampleDAO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InitializerService {
	
	private SampleDAO sampleDao;
	
	private ImageSetParser datasetParser;
	
	
	@Autowired
	public InitializerService(SampleDAO sampleDao, ImageSetParser datasetParser) {
		super();
		this.sampleDao = sampleDao;
		this.datasetParser = datasetParser;
	}

	
	public void initializeData(){
		
		int sizeOfTestData = initTestData();
		
		int devSizePerClass = sizeOfTestData / 10;
		
		List<Sample> trainSamples = datasetParser.readTrainSet();
		
		List<Sample> devSamples = substractDevSamples(devSizePerClass, trainSamples);
		
		sampleDao.saveDevSamples(devSamples);
		sampleDao.saveTrainSamples(trainSamples);
		
	}

	protected List<Sample> substractDevSamples(int devSizePerClass, List<Sample> trainSamples) {
		
		Map<Integer, List<Sample>> triainMap = trainSamples.stream().collect(Collectors.groupingBy(Sample::getLabel));
		
		List<Sample> devSamples = new ArrayList<>();
		
		Set<Integer> indexes = getRandomIndexes(devSizePerClass);
		
		triainMap.forEach((c, samples) -> {
			indexes.forEach(i -> {
				devSamples.add(samples.get(i));
			});
		});
		
		trainSamples.removeAll(devSamples);
		return devSamples;
	}
	
	protected Set<Integer> getRandomIndexes(int size){
		Random random = new Random();
		Set<Integer> indexes = new HashSet<>();
		
		for (int i = 0; i < size; i = indexes.size()) {
			indexes.add(random.nextInt(size));
		}
		
		return indexes;
	}
	
	protected int initTestData(){
		List<Sample> testSamples = datasetParser.readTestSet();
		sampleDao.saveTestSamples(testSamples);
		return testSamples.size();
		
	}

}
