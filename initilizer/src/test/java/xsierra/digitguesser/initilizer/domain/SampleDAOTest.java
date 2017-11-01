package xsierra.digitguesser.initilizer.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleDAOTest {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private SampleDAO dao;

	private Random random = new Random();

	@After
	public void tearDown() {
		template.execute("DELETE FROM train_sample");
	}

	@Test
	public void testSaveSamples() {

		List<Sample> samples = generateSamples(100);

		dao.saveSamples(samples);

		int numberOfSamples = template.queryForObject("SELECT count(*) FROM train_sample", Integer.class);

		assertEquals(100, numberOfSamples);
	}

	@Test
	public void testListAllSamples() {

		List<Sample> samples = generateSamples(10);
		dao.saveSamples(samples);

		List<Sample> obtainedSamples = dao.readAllSamples();

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
