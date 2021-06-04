package Task1.repository.impl;

import Task1.component.mapper.CarRowMapper;
import Task1.model.Car;
import Task1.model.CarStatusEnum;
import Task1.model.CarTypeEnum;
import Task1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepositoryImpl implements CarRepository {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CarRowMapper carRowMapper;

    @Override
    public Car get(Integer id) throws SQLException {
        String SQL = "select * from car where id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id");
                Integer curSpeed = resultSet.getInt("cur_speed");
                Integer normalSpeed = resultSet.getInt("normal_speed");
                Integer position = resultSet.getInt("position");
                Integer timeHardSlowing = resultSet.getInt("time_hard_slowing");
                Integer timeCrashed = resultSet.getInt("time_crashed");
                CarTypeEnum type = CarTypeEnum.PASSENGER;
                if (resultSet.getString("type").equals(CarTypeEnum.TRUCK.toString())) {
                    type = CarTypeEnum.TRUCK;
                }
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
                cars.add(new Car(id1, type, normalSpeed, curSpeed, status, position, timeHardSlowing, timeCrashed));
            }
            return cars.get(0);
        }
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = jdbcTemplate.query("select * from car", carRowMapper);
        return cars;
    }

    @Override
    public void update(Car car) {
        String SQL = "update car set type=?, normal_speed=?, cur_speed=?, status=?, position=?, time_hard_slowing=?, time_crashed=? where id=?";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, car.getType().toString());
            ps.setInt(2, car.getNormalSpeed());
            ps.setInt(3, car.getCurSpeed());
            ps.setString(4, car.getStatus().toString());
            ps.setInt(5, car.getPosition());
            ps.setInt(6, car.getTimeHardSlowing());
            ps.setInt(7, car.getTimeCrashed());
            ps.setInt(8, car.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insert(Car car) {
        String SQL = "insert into car (type, normal_speed, cur_speed, status, position, time_hard_slowing, time_crashed) values (?, ?,?,?,?,?,?)";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, car.getType().toString());
            ps.setInt(2, car.getNormalSpeed());
            ps.setInt(3, car.getCurSpeed());
            ps.setString(4, car.getStatus().toString());
            ps.setInt(5, car.getPosition());
            ps.setInt(6, car.getTimeHardSlowing());
            ps.setInt(7, car.getTimeCrashed());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(Car entity) {
        String SQL = "delete from car where id=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, entity.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            String SQL = "delete from car";
            st.execute(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
