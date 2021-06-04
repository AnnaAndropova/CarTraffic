package Task1.repository;

import Task1.domain.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository {

    Optional<Car> get(Integer id);

    List<Car> getAll();

    Car getNext(Car entity);

    List<Car> getPrev(Car entity);

    void update(Car entity);

    void insert(Car entity);

    void remove(Car entity);

    void clear();
}
