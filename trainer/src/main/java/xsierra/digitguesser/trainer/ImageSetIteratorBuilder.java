package xsierra.digitguesser.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsierra.digitguesser.initializer.domain.SampleDAO;

@Service
public class ImageSetIteratorBuilder{

    private SampleDAO sampleDAO;

    @Autowired
    public ImageSetIteratorBuilder(SampleDAO sampleDAO) {
        this.sampleDAO = sampleDAO;
    }

    public ImageSetIterator buildFromTrainingSet(int batch){
        return new ImageSetIterator(sampleDAO.readAllTraningSamples(), batch);
    }

    public ImageSetIterator buildFromTestSet(int batch){
        return new ImageSetIterator(sampleDAO.readAllTestSamples(), batch);
    }

    public ImageSetIterator buildFromDevSet(int batch){
        return new ImageSetIterator(sampleDAO.readAllDevSamples(), batch);
    }
}
