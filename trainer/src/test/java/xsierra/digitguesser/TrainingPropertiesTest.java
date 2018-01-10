package xsierra.digitguesser;

import org.junit.Before;
import org.junit.Test;
import xsierra.digitguesser.trainer.Context;
import xsierra.digitguesser.trainer.HyperParameter;

import static org.assertj.core.api.Assertions.*;

public class TrainingPropertiesTest {


    private TrainingProperties properties;

    @Before
    public void setUp() throws Exception {
        properties = new TrainingProperties();
        properties.setBatchSize(1);
        properties.setChannels(2);
        properties.setClasses(3);
        properties.setIterations(4);
        properties.setEpochs(5);
        properties.setPath("6");
        properties.setRandomSeed(7);
    }

    @Test
    public void buildTrainingContext() {

        Context context = properties.buildTrainingContext();

        Long batchSize = context.getHyperParameter(HyperParameter.BATCH_SIZE);
        Long channels = context.getHyperParameter(HyperParameter.NUMBER_OF_CHANNEL);
        Long classes = context.getHyperParameter(HyperParameter.NUMBER_OF_CLASSES);
        Long iterations = context.getHyperParameter(HyperParameter.NUMBER_OF_ITERATIONS);
        Long seed = context.getHyperParameter(HyperParameter.RANDOM_SEED);

        assertThat(batchSize).isEqualTo(1);
        assertThat(channels).isEqualTo(2);
        assertThat(classes).isEqualTo(3);
        assertThat(iterations).isEqualTo(4);
        assertThat(seed).isEqualTo(7);
        assertThat(properties.getPath()).isEqualTo("6");

    }

}