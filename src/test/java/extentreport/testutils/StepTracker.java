package testutils;

import io.cucumber.plugin.event.*;
import io.cucumber.plugin.ConcurrentEventListener;

public class StepTracker implements ConcurrentEventListener {

    private static final ThreadLocal<String> currentStep = new ThreadLocal<>();
    private static final ThreadLocal<String> currentScenario = new ThreadLocal<>();

    public static String getCurrentStep() {
        return currentStep.get();
    }

    public static String getCurrentScenario() {
        return currentScenario.get();
    }

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
            currentStep.set(pickleStep.getStep().getText());
        }
    }
}