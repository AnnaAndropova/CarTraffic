package Task1.service;

import Task1.component.RoadComponent;
import Task1.model.Car;
import Task1.model.Road;
import Task1.repository.CarRepository;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static java.lang.Thread.sleep;

@Service
public class TrafficService {

    @Autowired
    private ApplicationContext context;

    private final RoadCellRepository roadCellRepository;
    private final RoadRepository roadRepository;
    private final CarRepository carRepository;
    private final RoadGenerationService roadGenerationService;
    private final CarGenerationService carGenerationService;
    private final CarMovingService carMovingService;
    private final RoadComponent roadComponent;

    private int timeStep = 0;

    @Autowired
    public TrafficService(RoadCellRepository roadCellRepository, RoadRepository roadRepository, CarRepository carRepository, RoadGenerationService roadGenerationService, CarGenerationService carGenerationService, CarMovingService carMovingService, RoadComponent roadComponent) {
        this.roadCellRepository = roadCellRepository;
        this.roadRepository = roadRepository;
        this.carRepository = carRepository;
        this.roadGenerationService = roadGenerationService;
        this.carGenerationService = carGenerationService;
        this.carMovingService = carMovingService;
        this.roadComponent = roadComponent;
    }

    private void init() {
        roadCellRepository.clear();
        try {
            roadRepository.clear();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        carRepository.clear();
        roadGenerationService.init();
    }

    private int getTimeStep() {
        int max = roadComponent.getTimeStepMax();
        int min = roadComponent.getTimeStepMin();
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

    public void run() throws InterruptedException {
        init();
        Road road = roadRepository.getAll().get(0);
        while (true) {
            if (timeStep == 0) {
                carGenerationService.init();
                timeStep = getTimeStep();
            }
            List<Car> dtoList = carRepository.getAll();
            dtoList.sort(Car::compareTo);
            //Queue<CarDTO> dtoQueue = new LinkedList<>(dtoList);
            carMovingService.move(dtoList, road);
            timeStep--;
            sleep(1500);
        }
    }

}
