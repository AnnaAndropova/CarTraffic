package Task1.component.mapper;

import Task1.model.Car;
import Task1.model.CarStatusEnum;
import Task1.model.CarTypeEnum;
import Task1.model.Road;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet resultSet, int i) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt("id"));
        car.setCurSpeed(resultSet.getInt("cur_speed"));
        car.setNormalSpeed(resultSet.getInt("normal_speed"));
        car.setPosition(resultSet.getInt("position"));
        CarTypeEnum type = CarTypeEnum.PASSENGER;
        if (resultSet.getString("type").equals(CarTypeEnum.TRUCK.toString())) {
            type = CarTypeEnum.TRUCK;
        }
        car.setType(type);
        CarStatusEnum status = CarStatusEnum.MOVING;
        if (resultSet.getString("status").equals(CarStatusEnum.CRASHED.toString())) {
            status = CarStatusEnum.CRASHED;
        } else if (resultSet.getString("status").equals(CarStatusEnum.FINISHED_MOVING.toString())) {
            status = CarStatusEnum.FINISHED_MOVING;
        } else if (resultSet.getString("status").equals(CarStatusEnum.HARD_SLOWING_DOWN.toString())) {
            status = CarStatusEnum.HARD_SLOWING_DOWN;
        } else if (resultSet.getString("status").equals(CarStatusEnum.SLOWING_DOWN.toString())) {
            status = CarStatusEnum.SLOWING_DOWN;
        } else if (resultSet.getString("status").equals(CarStatusEnum.SPEEDING_UP.toString())) {
            status = CarStatusEnum.SPEEDING_UP;
        } else if (resultSet.getString("status").equals(CarStatusEnum.WAITING.toString())) {
            status = CarStatusEnum.WAITING;
        }
        car.setStatus(status);
        car.setTimeCrashed(resultSet.getInt("time_crashed"));
        car.setTimeHardSlowing(resultSet.getInt("time_hard_slowing"));
        return car;
    }
}
