package xsierra.digitguesser.drawer.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsierra.digitguesser.drawer.DigitGuesserService;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

@Service
public class PipelineDelegator implements DigitPipeline {

    private List<PipelineStep> steps;

    private DigitGuesserService model;

    @Autowired
    public PipelineDelegator(List<PipelineStep> steps, DigitGuesserService model) {
        this.steps = steps;
        this.model = model;
    }

    private static int compare(PipelineStep o1, PipelineStep o2) {
        Integer so1 = o1.getStepOrder();
        Integer so2 = o2.getStepOrder();
        return Integer.compare(so1, so2);
    }

    @PostConstruct
    public void init(){
        Collections.sort(steps, PipelineDelegator::compare);
    }

    @Override
    public byte imageGuessDigit(BufferedImage image) {

        BufferedImage processedImage = image;
        for (PipelineStep pipelineStep : steps) {
            processedImage = pipelineStep.processImage(processedImage);
        }

        return model.guessDigit(processedImage);
    }


}
