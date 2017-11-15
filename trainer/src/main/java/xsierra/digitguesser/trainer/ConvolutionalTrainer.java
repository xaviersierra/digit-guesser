package xsierra.digitguesser.trainer;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsierra.digitguesser.initializer.domain.SampleDAO;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ConvolutionalTrainer {

    private static final Logger log = getLogger(LenetMnistExample.class);

    private SampleDAO sampleDao;

    private ModelConfigurator modelConfigurer;

    private ImageSetIteratorBuilder iteratorBuiler;

    @Autowired
    public ConvolutionalTrainer(SampleDAO sampleDao, ModelConfigurator modelConfigurer, ImageSetIteratorBuilder iteratorBuiler) {
        this.sampleDao = sampleDao;
        this.modelConfigurer = modelConfigurer;
        this.iteratorBuiler = iteratorBuiler;
    }

    public MultiLayerNetwork trainModel(Context context) throws IOException {
        log.info("Training model");

        int batchSize = context.getHyperParameter(HyperParameter.BATCH_SIZE).intValue(); // Test batch size

        int nEpochs = context.getHyperParameter(HyperParameter.NUMBER_OF_EPOCHS).intValue();

        MultiLayerNetwork model = modelConfigurer.configureModel(context);

        DataSetIterator mnistTrain = iteratorBuiler.buildFromTrainingSet(batchSize);

        model.setListeners(new ScoreIterationListener(1));
        for( int i=0; i<nEpochs; i++ ) {
            model.fit(mnistTrain);
            log.info("*** Completed epoch {} ***", i);
        }
        log.info("Traning finished");
        return model;


    }


}
