package Task1.component.mapper;

import Task1.model.Road;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoadRowMapper implements RowMapper<Road> {

    @Override
    public Road mapRow(ResultSet resultSet, int i) throws SQLException {
        Road road = new Road();
        road.setId(resultSet.getInt("id"));
        road.setLength(resultSet.getInt("length"));
        return road;
    }
}
