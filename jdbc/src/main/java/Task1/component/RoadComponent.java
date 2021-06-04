package Task1.component;

import org.springframework.stereotype.Component;

@Component
public class RoadComponent {

    private static final int TIME_STEP_MIN = 5;
    private static final int TIME_STEP_MAX = 10;

    private static final int ROAD_LENGTH = 100;

    public int getTimeStepMin() {
        return TIME_STEP_MIN;
    }

    public int getTimeStepMax() {
        return TIME_STEP_MAX;
    }

    public int getRoadLength() {
        return ROAD_LENGTH;
    }
}
