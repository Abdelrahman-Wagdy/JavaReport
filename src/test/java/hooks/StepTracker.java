package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.*;

public class StepTracker implements ConcurrentEventListener {

    private static final ThreadLocal<String> CURRENT_STEP = new ThreadLocal<>();

    public static String currentStep() {        // helper for hooks
        return CURRENT_STEP.get();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::beforeStep);
    }

    private void beforeStep(TestStepStarted ev) {
        TestStep step = ev.getTestStep();
        if (step instanceof PickleStepTestStep pickleStep) {
            CURRENT_STEP.set(pickleStep.getStep().getText());
        }
    }
}