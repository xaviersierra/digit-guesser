package xsierra.digitguesser.initializer;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import xsierra.digitguesser.ServiceTestConfiguration;
import xsierra.digitguesser.initializer.ImageSetParser;
import xsierra.digitguesser.initializer.InitializerService;
import xsierra.digitguesser.initializer.domain.Sample;
import xsierra.digitguesser.initializer.domain.SampleDAO;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfiguration.class)
@ActiveProfiles("test")
public class InitializerServiceTest {

	@MockBean
	private SampleDAO sampleDao;

	@MockBean
	private ImageSetParser parser;

	@Autowired
	private InitializerService service;

	private List<Sample> testSet;

	private List<Sample> trainSet;

	private Random random = new Random();

	@Before
	public void setUp() throws Exception {

		trainSet = generateSamples(100);
		testSet = generateSamples(10);
	}

	@Test
	public void testInitializeData() {
		
		given(parser.readTestSet()).willReturn(testSet);
		given(parser.readTrainSet()).willReturn(trainSet);
		
		service.initializeData();
		
		verify(sampleDao).saveTestSamples(testSet);
		verify(sampleDao).saveDevSamples(anyListOf(Sample.class));
		verify(sampleDao).saveTrainSamples(trainSet);
	}
	
	@Test
	public void testSubstractDevSamples(){
		
		List<Sample> devSet = service.substractDevSamples(2, trainSet);
		
		assertThat(trainSet.size()).isEqualTo(80);
		assertThat(devSet.size()).isEqualTo(20);
		
		Map<Integer, List<Sample>> devSetMap = devSet.stream().collect(Collectors.groupingBy(Sample::getLabel));
		
		devSetMap.forEach((c, samples) -> {
			assertThat(samples.size()).isEqualTo(2);
		});
		
		assertThat(devSetMap.keySet().size()).isEqualTo(10);
		
		Map<Integer, List<Sample>> trainSetMap = trainSet.stream().collect(Collectors.groupingBy(Sample::getLabel));
		
		trainSetMap.forEach((c, samples) -> {
			assertThat(samples.size()).isEqualTo(8);
		});
		
		assertThat(trainSetMap.keySet().size()).isEqualTo(10);
		
		
	}

	@Test
	public void testGetRandomIndexes() {

		Set<Integer> indexes = service.getRandomIndexes(10);

		assertThat(indexes.size()).isEqualTo(10);

		Condition<Integer> areNotGraterThanTen = new Condition<Integer>() {
			@Override
			public boolean matches(Integer value) {
				return value >= 10;
			}
		};
		assertThat(indexes).areNot(areNotGraterThanTen);

	}

	@Test
	public void testInitTestData() {

		given(parser.readTestSet()).willReturn(testSet);
		
		service.initTestData();

		verify(sampleDao).saveTestSamples(testSet);

	}

	private int[] generateImage(int numberOfPixels) {
		int[] image = new int[numberOfPixels];
		for (int i = 0; i < numberOfPixels; i++) {
			image[i] = random.nextInt(256);
		}
		return image;
	}

	private List<Sample> generateSamples(int numberOfSamples) {
		List<Sample> samples = new ArrayList<>();

		for (int i = 0; i < numberOfSamples; i++) {
			Sample sample = new Sample();
			sample.setId(i);
			sample.setImage(generateImage(400));
			sample.setLabel(i % 10);
			samples.add(sample);
		}
		return samples;
	}

}
