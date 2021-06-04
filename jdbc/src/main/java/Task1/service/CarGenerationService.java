package Task1.service;

import Task1.component.CarComponent;
import Task1.model.Car;
import Task1.model.CarStatusEnum;
import Task1.model.CarTypeEnum;
import Task1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarGenerationService {

    private final CarRepository carRepository;

    private final CarComponent carComponent;

    @Autowired
    public CarGenerationService(CarRepository carRepository, CarComponent carComponent) {
        this.carRepository = carRepository;
        this.carComponent = carComponent;
    }




    public void init() {

        Car car = new Car();
        double tmp = Math.random();
        if (1 - tmp < 0.5) {
            car.setType(CarTypeEnum.TRUCK);
            car.setCurSpeed((int) ((Math.random() * carComponent.getTruckSpeedMax()) + carComponent.getTruckSpeedMin()));
        } else {
            car.setType(CarTypeEnum.PASSENGER);
            car.setCurSpeed((int) ((Math.random() * carComponent.getPassengerSpeedMax()) + carComponent.getPassengerSpeedMin()));
        }

        car.setStatus(CarStatusEnum.MOVING);
        car.setTimeHardSlowing(0);
        car.setTimeCrashed(0);
        car.setPosition(0);
        car.setNormalSpeed(car.getCurSpeed());
        carRepository.insert(car);

    }
}
