package Task1.service;

import Task1.component.RoadComponent;
import Task1.domain.dto.CarDTO;
import Task1.domain.dto.RoadDTO;
import Task1.component.mapper.MainMapper;
import Task1.repository.CarRepository;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
    private final MainMapper mapper;

    private int timeStep = 0;

    @Autowired
    public TrafficService(RoadCellRepository roadCellRepository, RoadRepository roadRepository, CarRepository carRepository, RoadGenerationService roadGenerationService, CarGenerationService carGenerationService, CarMovingService carMovingService, RoadComponent roadComponent, MainMapper mapper) {
        this.roadCellRepository = roadCellRepository;
        this.roadRepository = roadRepository;
        this.carRepository = carRepository;
        this.roadGenerationService = roadGenerationService;
        this.carGenerationService = carGenerationService;
        this.carMovingService = carMovingService;
        this.roadComponent = roadComponent;
        this.mapper = mapper;
    }

    private void init() {
        roadCellRepository.clear();
        roadRepository.clear();
        carRepository.clear();
        roadGenerationService.init();
    }

    private int getTimeStep(){
        int max = roadComponent.getTimeStepMax();
        int min = roadComponent.getTimeStepMin();
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

    public void run() throws InterruptedException {
        init();
        RoadDTO roadDTO = mapper.road2roadDto(roadRepository.getAll().get(0));
        while (true) {
            if (timeStep == 0) {
                carGenerationService.init();
                timeStep = getTimeStep();
            }
            List<CarDTO> dtoList = mapper.car2carDto(carRepository.getAll());
            dtoList.sort(CarDTO::compareTo);
            //Queue<CarDTO> dtoQueue = new LinkedList<>(dtoList);
            carMovingService.move(dtoList, roadDTO);
            timeStep--;
            sleep(1500);
        }
    }

}
