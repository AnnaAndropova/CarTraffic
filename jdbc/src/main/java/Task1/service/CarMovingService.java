package Task1.service;

import Task1.component.CarComponent;
import Task1.component.RoadComponent;
import Task1.model.*;
import Task1.repository.CarRepository;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CarMovingService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RoadComponent roadComponent;
    @Autowired
    private CarComponent carComponent;
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


    public void draw(Road road, List<RoadCell> cells) {
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

    public void move(List<Car> cars, Road road) {
        List<RoadCell> cells = roadCellRepository.getAll();

        for (Car car : cars) {
            crash(cars, car);
        }
        draw(road, cells);
        boolean isFinished = false;
        for (Car car : cars) {
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
            Car carToRemove = cars.get(0);
            cars.remove(carToRemove);
            if (carToRemove != null) {
                carRepository.remove(carToRemove);
            }
        }
        for (Car car : cars) {
            changeCoordinate(cars, car, cells);
        }
        for (Car car : cars) {
            carRepository.update(car);
        }
    }

    private void changeCoordinate(List<Car> cars, Car car, List<RoadCell> cells) {
        try {
            roadCellRepository.findAndClear(carRepository.get(car.getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int nextPosition = car.getPosition() + car.getCurSpeed();
        if (nextPosition >= roadComponent.getRoadLength()) {
            nextPosition = roadComponent.getRoadLength();
            car.setStatus(CarStatusEnum.FINISHED_MOVING);
        }
        Car prev = getPrev(cars, car);
        if (prev != null && prev.getPosition() - getLength(prev) <= nextPosition) {
            nextPosition = prev.getPosition() - getLength(prev) - 1;
        }
        car.setPosition(nextPosition);
        if (car.getStatus() != CarStatusEnum.FINISHED_MOVING) {
            for (int i = 0; i < getLength(car); i++) {
                if (nextPosition - i >= 0) {
                    cells.get(nextPosition - i).setCar(car);
                    roadCellRepository.update((cells.get(nextPosition - i)));
                }
            }
        }
    }

    private void crash(List<Car> cars, Car car) {
        Car next = getNext(cars, car);
        if (next != null && next.getCurSpeed() != 0 &&
                car.getPosition() - getLength(car) - next.getPosition() <= getLength(next)) {
            doCrash(car, car.getTimeCrashed() + 1);
            doCrash(next, car.getTimeCrashed() + 2);
        }
    }

    private void doCrash(Car car, int time) {
        car.setCurSpeed(0);
        car.setTimeHardSlowing(0);
        car.setTimeCrashed(time);
        car.setStatus(CarStatusEnum.CRASHED);
    }

    private void hardSlowDown(Car car) {
        double chance = Math.random();
        if (1 - chance >= 0.95) {
            int speed = Math.max(car.getCurSpeed() - 2 * getBoost(car), 0);
            car.setCurSpeed(speed);
            car.setTimeHardSlowing(getRandom());
            car.setStatus(CarStatusEnum.HARD_SLOWING_DOWN);
        }
    }

    private void slowDown(List<Car> cars, Car car) {
        car.setStatus(CarStatusEnum.MOVING);
        Car prev = getPrev(cars, car);
        if (prev != null && car.getCurSpeed() > prev.getCurSpeed()) {
            if (prev.getPosition() - getLength(prev) - car.getPosition() <= getLength(car) * 3) {
                int speed = Math.max(prev.getCurSpeed(), car.getCurSpeed() - getBoost(car));
                car.setCurSpeed(speed);
                car.setStatus(CarStatusEnum.SLOWING_DOWN);
            }
        }
        if (car.getCurSpeed() == 0) car.setStatus(CarStatusEnum.WAITING);
    }

    private void speedUp(List<Car> cars, Car car) {
        car.setStatus(CarStatusEnum.MOVING);
        if (car.getCurSpeed() < car.getNormalSpeed()) {
            Car prev = getPrev(cars, car);
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

    private Car getNext(List<Car> cars, Car car) {
        int id = car.getId();
        for (Car c : cars) {
            if (c.getId() > id) {
                id = c.getId();
                try {
                    return carRepository.get(id);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    private Car getPrev(List<Car> cars, Car car) {
        int id = car.getId();
        for (Car c : cars) {
            if (c.getId() < id) {
                id = c.getId();
            }
        }
        if (id == car.getId()) return null;
        try {
            return carRepository.get(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private int getRandom() {
        int min = carComponent.getTimeSlowDownMin();
        int max = carComponent.getTimeSlowDownMax();
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }

    private int getLength(Car car) {
        int length = carComponent.getPassengerLength();
        if (car.getType() == CarTypeEnum.TRUCK) {
            length = carComponent.getTruckLength();
        }
        return length;
    }

    private int getBoost(Car car) {
        int boost = carComponent.getPassengerBoost();
        if (car.getType() == CarTypeEnum.TRUCK) {
            boost = carComponent.getTruckBoost();
        }
        return boost;
    }
}
