package Task1.repository;

import Task1.domain.entity.Road;

import java.util.List;
import java.util.Optional;

public interface RoadRepository {
    Optional<Road> get(Long id);

    List<Road> getAll();

    void update(Road entity);

    void insert(Road entity);

    void save(Road entity);

    void clear();
}
