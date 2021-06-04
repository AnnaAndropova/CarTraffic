package Task1.service;

import Task1.component.CarComponent;
import Task1.domain.dto.CarDTO;
import Task1.domain.dto.CarStatusEnum;
import Task1.domain.dto.CarTypeEnum;
import Task1.component.mapper.MainMapper;
import Task1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarGenerationService {

    private final MainMapper mapper;
    private final CarRepository carRepository;

    private final CarComponent carComponent;

    @Autowired
    public CarGenerationService(MainMapper mapper, CarRepository carRepository, CarComponent carComponent) {
        this.mapper = mapper;
        this.carRepository = carRepository;
        this.carComponent = carComponent;
    }




    public void init() {

        CarDTO carDTO = new CarDTO();
        double tmp = Math.random();
        if (1 - tmp < 0.5) {
            carDTO.setType(CarTypeEnum.TRUCK);
            carDTO.setCurSpeed((int) ((Math.random() * carComponent.getTruckSpeedMax()) + carComponent.getTruckSpeedMin()));
        } else {
            carDTO.setType(CarTypeEnum.PASSENGER);
            carDTO.setCurSpeed((int) ((Math.random() * carComponent.getPassengerSpeedMax()) + carComponent.getPassengerSpeedMin()));
        }

        carDTO.setStatus(CarStatusEnum.MOVING);
        carDTO.setTimeHardSlowing(0);
        carDTO.setTimeCrashed(0);
        carDTO.setPosition(0);
        carDTO.setNormalSpeed(carDTO.getCurSpeed());
        carRepository.insert(mapper.carDto2car(carDTO));

    }
}
