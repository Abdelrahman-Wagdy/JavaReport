package hooks;

import basetest.StepsTrackerManager;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.*;

public class StepTracker implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::beforeStep);
    }

    private void beforeStep(TestStepStarted ev) {
        TestStep step = ev.getTestStep();
        if (step instanceof PickleStepTestStep pickleStep) {
            StepsTrackerManager.setCurrentStep(pickleStep.getStep().getText());
        }
    }
}