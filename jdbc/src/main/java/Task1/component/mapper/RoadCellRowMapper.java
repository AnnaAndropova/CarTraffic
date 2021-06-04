package Task1.component.mapper;

import Task1.model.Car;
import Task1.model.CarStatusEnum;
import Task1.model.CarTypeEnum;
import Task1.model.RoadCell;
import Task1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoadCellRowMapper implements RowMapper<RoadCell> {
    @Autowired
    private CarRepository carRepository;
    @Override
    public RoadCell mapRow(ResultSet resultSet, int i) throws SQLException {
        RoadCell roadCell = new RoadCell();
        roadCell.setId(resultSet.getInt("id"));
        roadCell.setCar(null);
        if (resultSet.getObject("car_id") != null) {
            roadCell.setCar(carRepository.get(resultSet.getInt("car_id")));
        }
        roadCell.setRoadId(resultSet.getInt("road_id"));
        return roadCell;
    }
}
