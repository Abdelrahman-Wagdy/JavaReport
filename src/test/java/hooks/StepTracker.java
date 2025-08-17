package hooks;

import basetest.StepsTrackerManager;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.*;

public class StepTracker implements ConcurrentEventListener {
    private static final ThreadLocal<String> currentStep = new ThreadLocal<>();
    private static final ThreadLocal<String> currentScenario = new ThreadLocal<>();


    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        currentScenario.set(event.getTestCase().getName());
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep pickleStep) {
            String stepText = pickleStep.getStep().getText();
            currentStep.set(stepText);
            StepsTrackerManager.setCurrentStep(stepText); // <--- This is the missing link
        }
    }
}