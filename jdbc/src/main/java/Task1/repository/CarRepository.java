package Task1.repository;


import Task1.model.Car;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface CarRepository {

    Car get(Integer id) throws SQLException;

    List<Car> getAll();

    void update(Car entity);

    void insert(Car entity);

    void remove(Car entity);

    void clear();
}
