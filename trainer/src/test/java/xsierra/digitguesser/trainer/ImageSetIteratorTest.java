package xsierra.digitguesser.trainer;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.dataset.DataSet;
import xsierra.digitguesser.initializer.domain.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class ImageSetIteratorTest {

    private ImageSetIterator imageSetIterator;

    @Before
    public void setUp(){

        List<Sample> samples = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            samples.add(generateRandomSample());
        }

        imageSetIterator = new ImageSetIterator(samples, 20);
    }

    private Sample generateRandomSample(){
        Sample s = new Sample();
        int[] image = new int[400];
        Random random = new Random();
        for (int i = 0; i < image.length; i++) {
            int pixel = random.nextInt(256);
            image[i] = pixel;
        }
        s.setImage(image);
        s.setLabel(random.nextInt(10));
        return s;
    }

    @Test
    public void next() throws Exception {
        DataSet dataset = imageSetIterator.next();
        assertThat(dataset.numExamples()).isEqualTo(20);
    }

    @Test
    public void totalExamples() throws Exception {
        assertThat(imageSetIterator.totalExamples()).isEqualTo(100);
    }

    @Test
    public void inputColumns() throws Exception {
        assertThat(imageSetIterator.inputColumns()).isEqualTo(400);
    }

    @Test
    public void totalOutcomes() throws Exception {
        assertThat(imageSetIterator.totalOutcomes()).isEqualTo(10);
    }

    @Test
    public void resetSupported() throws Exception {
        assertThat(imageSetIterator.resetSupported()).isEqualTo(false);
    }

    @Test
    public void asyncSupported() throws Exception {
        assertThat(imageSetIterator.asyncSupported()).isEqualTo(false);
    }

    @Test
    public void reset() throws Exception {
        imageSetIterator.reset();
    }

    @Test
    public void batch() throws Exception {
        assertThat(imageSetIterator.batch()).isEqualTo(20);
    }

    @Test
    public void cursor() throws Exception {
        imageSetIterator.next();
        assertThat(imageSetIterator.cursor()).isEqualTo(20);
    }

    @Test
    public void numExamples() throws Exception {
        assertThat(imageSetIterator.numExamples()).isEqualTo(100);
    }

    @Test
    public void setPreProcessor() throws Exception {
        imageSetIterator.setPreProcessor(null);
    }

    @Test
    public void getPreProcessor() throws Exception {
        imageSetIterator.getPreProcessor();
    }

    @Test
    public void getLabels() throws Exception {
        assertThat(imageSetIterator.getLabels()).isNull();
    }

    @Test
    public void hasNext() throws Exception {
        assertThat(imageSetIterator.hasNext()).isTrue();
        imageSetIterator.next(100);
        assertThat(imageSetIterator.hasNext()).isFalse();
    }

    @Test
    public void next1() throws Exception {
        DataSet dataSet = imageSetIterator.next(99);
        assertThat(dataSet.numExamples()).isEqualTo(99);
    }

}