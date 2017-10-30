package xsierra.digitguesser.initilizer;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdxImageParserTest {

	@MockBean
	private ImportProperties properties;

	@Autowired
	private IdxImageParser imageParser;

	@Test
	public void testReadTrainSet() {
		given(properties.getTrainingImageFile()).willReturn("Invalid File");
		given(properties.getTrainingLabelFile()).willReturn("Invalid File");

		try {
			imageParser.readTrainSet();
		} catch (ImportSamplesException e) {
		}

		verify(properties, never()).getTestImageFile();
		verify(properties, never()).getTestLabelFile();
	}

	@Test
	public void testReadTestSet() {
		given(properties.getTestImageFile()).willReturn("Invalid File");
		given(properties.getTestLabelFile()).willReturn("Invalid File");

		try {
			imageParser.readTestSet();
		} catch (ImportSamplesException e) {
		}

		verify(properties, never()).getTrainingImageFile();
		verify(properties, never()).getTrainingLabelFile();
	}

	@Test
	public void testImportImages() {

		List<Sample> samples = imageParser.importImages("t10k-images-idx3-ubyte.gz", "t10k-labels-idx1-ubyte.gz");

		assertThat(samples.size()).isEqualTo(10000);
	}

}
