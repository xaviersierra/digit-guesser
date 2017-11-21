package xsierra.digitguesser.drawer.pipeline;

public abstract class OrderablePipelineStep implements PipelineStep {
    protected int stepOpder;

    public OrderablePipelineStep(int stepOpder) {
        this.stepOpder = stepOpder;
    }

    @Override
    public int getStepOrder() {
        return stepOpder;
    }
}
