package Task1.service;

import Task1.component.CarComponent;
import Task1.component.RoadComponent;
import Task1.domain.dto.CarDTO;
import Task1.domain.dto.CarStatusEnum;
import Task1.domain.dto.CarTypeEnum;
import Task1.domain.dto.RoadDTO;
import Task1.domain.entity.RoadCell;
import Task1.component.mapper.MainMapper;
import Task1.repository.CarRepository;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarMovingService {

    @Autowired
    private MainMapper mapper;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RoadComponent roadComponent;
    @Autowired
    private CarComponent carComponent;
    @Autowired
    private RoadRepository roadRepository;
    @Autowired
    private RoadCellRepository roadCellRepository;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public void draw(RoadDTO road, List<RoadCell> cells) {
        for (int j = 0; j < 3; j++) {
            if (j == 0) {
                for (int i = 0; i < road.getLength(); i++) {
                    System.out.print("__");
                }
                System.out.println();
            } else if (j == 1) {
                for (int i = 0; i < road.getLength(); i++) {
                    if (cells.get(i).getCar() != null) {
                        switch (cells.get(i).getCar().getStatus()) {
                            case MOVING:
                                System.out.print("|*");
                                break;
                            case WAITING:
                                System.out.print("|" + ANSI_BLUE + "*" + ANSI_RESET + "");
                                break;
                            case HARD_SLOWING_DOWN:
                                System.out.print("|" + ANSI_RED + "*" + ANSI_RESET + "");
                                break;
                            case CRASHED:
                                System.out.print("|" + ANSI_PURPLE + "*" + ANSI_RESET + "");
                                break;
                            case SPEEDING_UP:
                                System.out.print("|" + ANSI_GREEN + "*" + ANSI_RESET + "");
                                break;
                            case SLOWING_DOWN:
                                System.out.print("|" + ANSI_YELLOW + "*" + ANSI_RESET + "");
                                break;
                        }
                    } else {
                        System.out.print("| ");
                    }
                }
                System.out.println();
            } else {
                for (int i = 0; i < road.getLength(); i++) {
                    System.out.print("‾‾");
                }
                System.out.println();
            }
        }
    }

    public void move(List<CarDTO> cars, RoadDTO road) {
        List<RoadCell> cells = roadCellRepository.getAll();

        for (CarDTO car : cars) {
            crash(cars, car);
        }
        draw(road, cells);
        boolean isFinished = false;
        for (CarDTO car : cars) {
            if (car.getTimeCrashed() != 0) {
                car.setTimeCrashed(car.getTimeCrashed() - 1);
            } else {
                if (car.getStatus() != CarStatusEnum.FINISHED_MOVING) {
                    if (car.getTimeCrashed() == 0) {
                        if (car.getTimeHardSlowing() == 0) {
                            hardSlowDown(car);
                            if (car.getTimeHardSlowing() == 0) {
                                speedUp(cars, car);
                                if (car.getStatus() == CarStatusEnum.MOVING) slowDown(cars, car);
                            }
                        } else {
                            if (car.getCurSpeed() > 0) {
                                car.setStatus(CarStatusEnum.MOVING);
                            } else {
                                car.setStatus(CarStatusEnum.WAITING);
                            }
                            car.setTimeHardSlowing(car.getTimeHardSlowing() - 1);
                        }
                    }
                } else {
                    isFinished = true;
                }
            }
        }
        if (isFinished) {
            CarDTO carToRemove = cars.get(0);
            cars.remove(carToRemove);
            if (carToRemove != null) {
                carRepository.remove(mapper.carDto2car(carToRemove));
            }
        }
        for (CarDTO car : cars) {
            changeCoordinate(cars, car, cells);
        }
        for (CarDTO car : cars) {
            carRepository.update(mapper.carDto2car(car));
        }
    }

    private void changeCoordinate(List<CarDTO> cars, CarDTO car, List<RoadCell> cells) {
        roadCellRepository.findAndClear(carRepository.get(car.getId()).orElse(null));
        int nextPosition = car.getPosition() + car.getCurSpeed();
        if (nextPosition >= roadComponent.getRoadLength()) {
            nextPosition = roadComponent.getRoadLength();
            car.setStatus(CarStatusEnum.FINISHED_MOVING);
        }
        CarDTO prev = getPrev(cars, car);
        if (prev != null && prev.getPosition() - getLength(prev) <= nextPosition) {
            nextPosition = prev.getPosition() - getLength(prev) - 1;
        }
        car.setPosition(nextPosition);
        if (car.getStatus() != CarStatusEnum.FINISHED_MOVING) {
            for (int i = 0; i < getLength(car); i++) {
                if (nextPosition - i >= 0) {
                    cells.get(nextPosition - i).setCar(carRepository.get(car.getId()).orElse(null));
                    roadCellRepository.update((cells.get(nextPosition - i)));
                }
            }
        }
    }

    private void crash(List<CarDTO> cars, CarDTO car) {
        CarDTO next = getNext(cars, car);
        if (next != null && next.getCurSpeed() != 0 &&
                car.getPosition() - getLength(car) - next.getPosition() <= getLength(next)) {
            doCrash(car, car.getTimeCrashed() + 1);
            doCrash(next, car.getTimeCrashed() + 2);
        }
    }

    private void doCrash(CarDTO car, int time) {
        car.setCurSpeed(0);
        car.setTimeHardSlowing(0);
        car.setTimeCrashed(time);
        car.setStatus(CarStatusEnum.CRASHED);
    }

    private void hardSlowDown(CarDTO car) {
        double chance = Math.random();
        if (1 - chance >= 0.95) {
            int speed = Math.max(car.getCurSpeed() - 2 * getBoost(car), 0);
            car.setCurSpeed(speed);
            car.setTimeHardSlowing(getRandom());
            car.setStatus(CarStatusEnum.HARD_SLOWING_DOWN);
        }
    }

    private void slowDown(List<CarDTO> cars, CarDTO car) {
        car.setStatus(CarStatusEnum.MOVING);
        CarDTO prev = getPrev(cars, car);
        if (prev != null && car.getCurSpeed() > prev.getCurSpeed()) {
            if (prev.getPosition() - getLength(prev) - car.getPosition() <= getLength(car) * 3) {
                int speed = Math.max(prev.getCurSpeed(), car.getCurSpeed() - getBoost(car));
                car.setCurSpeed(speed);
                car.setStatus(CarStatusEnum.SLOWING_DOWN);
            }
        }
        if (car.getCurSpeed() == 0) car.setStatus(CarStatusEnum.WAITING);
    }

    private void speedUp(List<CarDTO> cars, CarDTO car) {
        car.setStatus(CarStatusEnum.MOVING);
        if (car.getCurSpeed() < car.getNormalSpeed()) {
            CarDTO prev = getPrev(cars, car);
            int speed = Math.min(car.getCurSpeed() + getBoost(car), car.getNormalSpeed());
            if (prev != null) {
                if (prev.getPosition() - getLength(prev) - car.getPosition() <= getLength(car) * 3 &&
                        prev.getPosition() - getLength(prev) - car.getPosition() > getLength(car)) {
                    speed = Math.min(speed, prev.getCurSpeed());
                } else if (prev.getPosition() - getLength(prev) - car.getPosition() <= getLength(car)) {
                    return;
                }
            }
            if (car.getCurSpeed() < speed) {
                car.setCurSpeed(speed);
                if (car.getCurSpeed() != 0)
                    car.setStatus(CarStatusEnum.SPEEDING_UP);
            }
        }
    }

    private CarDTO getNext(List<CarDTO> cars, CarDTO car) {
        int id = car.getId();
        for (CarDTO c : cars) {
            if (c.getId() > id) {
                id = c.getId();
                return mapper.car2carDto(carRepository.get(id).get());
            }
        }
        return null;
    }

    private CarDTO getPrev(List<CarDTO> cars, CarDTO car) {
        int id = car.getId();
        for (CarDTO c : cars) {
            if (c.getId() < id) {
                id = c.getId();
            }
        }
        if (id == car.getId()) return null;
        return mapper.car2carDto(carRepository.get(id).get());
    }

    private int getRandom() {
        int min = carComponent.getTimeSlowDownMin();
        int max = carComponent.getTimeSlowDownMax();
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

    private int getLength(CarDTO car) {
        int length = carComponent.getPassengerLength();
        if (car.getType() == CarTypeEnum.TRUCK) {
            length = carComponent.getTruckLength();
        }
        return length;
    }

    private int getBoost(CarDTO car) {
        int boost = carComponent.getPassengerBoost();
        if (car.getType() == CarTypeEnum.TRUCK) {
            boost = carComponent.getTruckBoost();
        }
        return boost;
    }
}
