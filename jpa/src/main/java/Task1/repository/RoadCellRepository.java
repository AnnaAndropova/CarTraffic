package Task1.repository;

import Task1.domain.entity.Car;
import Task1.domain.entity.RoadCell;

import java.util.List;

public interface RoadCellRepository {
    RoadCell get(Integer id);

    List<RoadCell> getAll();

    List<RoadCell> getAllByCar(Car car);

    void update(RoadCell entity);

    void insert(RoadCell newEntity);

    void findAndClear(Car entity);

    void clear();
}
