package basetest;

public class StepsTrackerManager {
    private static final ThreadLocal<String> CURRENT_STEP = new ThreadLocal<>();

    public static String getCurrentStep() {        // helper for hooks
        return CURRENT_STEP.get();
    }

    public static void setCurrentStep(String stepName){
        CURRENT_STEP.set(stepName);
    }

    public static void clearCurrentStep() {
        CURRENT_STEP.remove();
    }
}
