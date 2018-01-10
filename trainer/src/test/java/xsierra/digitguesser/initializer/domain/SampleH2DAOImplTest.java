package xsierra.digitguesser.initializer.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import xsierra.digitguesser.ServiceTestConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfiguration.class)
@ActiveProfiles("test")
public class SampleH2DAOImplTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private SampleDAO dao;

	private Random random = new Random();

	@After
	public void tearDown() {
		template.execute("DELETE FROM train_sample");
		template.execute("DELETE FROM dev_sample");
		template.execute("DELETE FROM test_sample");
	}

	@Test
	public void testSaveTrainSamples() {

		List<Sample> samples = generateSamples(100);

		dao.saveTrainSamples(samples);

		int numberOfSamples = template.queryForObject("SELECT count(*) FROM train_sample", Integer.class);

		assertEquals(100, numberOfSamples);
	}

	@Test
	public void testListAllTrainSamples() {

		List<Sample> samples = generateSamples(10);
		dao.saveTrainSamples(samples);

		List<Sample> obtainedSamples = dao.readAllTraningSamples();

		assertEquals(10, obtainedSamples.size());

	}

	@Test
	public void testSaveTestSamples() {

		List<Sample> samples = generateSamples(100);

		dao.saveTestSamples(samples);

		int numberOfSamples = template.queryForObject("SELECT count(*) FROM test_sample", Integer.class);

		assertEquals(100, numberOfSamples);
	}

	@Test
	public void testListAllTestSamples() {

		List<Sample> samples = generateSamples(10);
		dao.saveTestSamples(samples);

		List<Sample> obtainedSamples = dao.readAllTestSamples();

		assertEquals(10, obtainedSamples.size());

	}

	@Test
	public void testDevTestSamples() {

		List<Sample> samples = generateSamples(100);

		dao.saveDevSamples(samples);

		int numberOfSamples = template.queryForObject("SELECT count(*) FROM dev_sample", Integer.class);

		assertEquals(100, numberOfSamples);
	}

	@Test
	public void testListAllDevSamples() {

		List<Sample> samples = generateSamples(10);
		dao.saveDevSamples(samples);

		List<Sample> obtainedSamples = dao.readAllDevSamples();

		assertEquals(10, obtainedSamples.size());

	}

	private int[] generateImage() {
		int[] image = new int[400];
		for (int i = 0; i < 400; i++) {
			image[i] = random.nextInt(256);
		}
		return image;
	}

	private List<Sample> generateSamples(int numberOfSamples) {
		List<Sample> samples = new ArrayList<>();

		for (int i = 0; i < numberOfSamples; i++) {
			Sample sample = new Sample();
			sample.setId(i);
			sample.setImage(generateImage());
			sample.setLabel(random.nextInt(10));
			samples.add(sample);
		}
		return samples;
	}

}
