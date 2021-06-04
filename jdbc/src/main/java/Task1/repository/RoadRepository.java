package Task1.repository;

import Task1.model.Road;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoadRepository {
    Road get(Long id) throws SQLException;

    List<Road> getAll();

    void update(Road entity) throws SQLException;

    void insert(Road entity) throws SQLException;

    void clear() throws SQLException;
}
