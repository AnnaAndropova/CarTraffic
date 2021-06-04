package Task1.component;

import org.springframework.stereotype.Component;

@Component
public class CarComponent {

    private static final int TIME_SLOW_DOWN_MIN = 1;
    private static final int TIME_SLOW_DOWN_MAX = 3;

    private static final int PASSENGER_SPEED_MIN = 5;
    private static final int PASSENGER_SPEED_MAX = 8;
    private static final int PASSENGER_BOOST = 3;
    private static final int PASSENGER_LENGTH = 3;

    private static final int TRUCK_SPEED_MIN = 3;
    private static final int TRUCK_SPEED_MAX = 6;
    private static final int TRUCK_BOOST = 2;
    private static final int TRUCK_LENGTH = 5;

    public int getTimeSlowDownMin() {
        return TIME_SLOW_DOWN_MIN;
    }

    public int getTimeSlowDownMax() {
        return TIME_SLOW_DOWN_MAX;
    }

    public int getPassengerSpeedMin() {
        return PASSENGER_SPEED_MIN;
    }

    public int getPassengerSpeedMax() {
        return PASSENGER_SPEED_MAX;
    }

    public int getPassengerBoost() {
        return PASSENGER_BOOST;
    }

    public int getPassengerLength() {
        return PASSENGER_LENGTH;
    }

    public int getTruckSpeedMin() {
        return TRUCK_SPEED_MIN;
    }

    public int getTruckSpeedMax() {
        return TRUCK_SPEED_MAX;
    }

    public int getTruckBoost() {
        return TRUCK_BOOST;
    }

    public int getTruckLength() {
        return TRUCK_LENGTH;
    }
}
