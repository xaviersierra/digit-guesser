package xsierra.digitguesser.trainer;

import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import xsierra.digitguesser.Trainer;
import xsierra.digitguesser.initializer.InitializerService;
import xsierra.digitguesser.initializer.domain.SampleDAO;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvolutionalTrainerTest {

    @Autowired
    private ConvolutionalTrainer trainer;

    @Autowired
    private InitializerService initializerService;

    @Autowired
    private ImageSetIteratorBuilder iteratorBuilder;

    private Context context;

    private DataSetIterator mnistTest;

    private DataSetIterator mnistDev;

    @Before
    public void init() throws Exception{

        initializerService.initializeData();

        context = new Context();
        context.putHyperParameter(HyperParameter.BATCH_SIZE, 64L);
        context.putHyperParameter(HyperParameter.NUMBER_OF_CHANNEL, 1L);
        context.putHyperParameter(HyperParameter.NUMBER_OF_CLASSES, 10L);
        context.putHyperParameter(HyperParameter.NUMBER_OF_EPOCHS, 1L);
        context.putHyperParameter(HyperParameter.NUMBER_OF_ITERATIONS, 1L);
        context.putHyperParameter(HyperParameter.RANDOM_SEED, 123L);

        int batchSize = context.getHyperParameter(HyperParameter.BATCH_SIZE).intValue();
        mnistTest = iteratorBuilder.buildFromTestSet(batchSize);
        mnistDev = iteratorBuilder.buildFromDevSet(batchSize);


    }

    @Test
    public void trainModel() throws Exception {

        MultiLayerNetwork model = trainer.trainModel(context);

        Evaluation eval = model.evaluate(mnistTest);

        assertThat(eval.accuracy()).isGreaterThan(0.95d);

        System.out.println("Training accuracy test set=" + eval.accuracy());

        eval = model.evaluate(mnistDev);

        assertThat(eval.accuracy()).isGreaterThan(0.95d);

        System.out.println("Training accuracy CV set=" + eval.accuracy());

    }

}