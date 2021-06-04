package Task1.repository;


import Task1.model.Car;
import Task1.model.RoadCell;

import java.sql.SQLException;
import java.util.List;

public interface RoadCellRepository {

    RoadCell get(Integer id) throws SQLException;

    List<RoadCell> getAll();

    void update(RoadCell entity);

    void insert(RoadCell newEntity) throws SQLException;

    void findAndClear(Car entity);

    void clear();
}
