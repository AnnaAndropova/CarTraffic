package Task1.repository.impl;

import Task1.component.mapper.RoadCellRowMapper;
import Task1.model.Car;
import Task1.model.RoadCell;
import Task1.repository.CarRepository;
import Task1.repository.RoadCellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoadCellRepositoryImpl implements RoadCellRepository {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoadCellRowMapper roadCellRowMapper;

    @Autowired
    private CarRepository carRepository;

    @Override
    public RoadCell get(Integer id) throws SQLException {
        String SQL = "select * from road_cell where id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            List<RoadCell> roadCells = new ArrayList<>();
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id");
                Car car = carRepository.get(resultSet.getInt("car_id"));
                Integer road = resultSet.getInt("road_id");
                roadCells.add(new RoadCell(id1, car, road));
            }
            return roadCells.get(0);
        }
    }


    @Override
    public List<RoadCell> getAll() {
        List<RoadCell> roadCells = jdbcTemplate.query("select * from road_cell order by id", roadCellRowMapper);
        return roadCells;
    }

    @Override
    public void update(RoadCell entity) {
        String SQL = "update road_cell set car_id=?, road_id=? where id=?";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, null);
            if (entity.getCar()!= null) {
                ps.setInt(1, entity.getCar().getId());
            }
            ps.setInt(2, entity.getRoadId());
            ps.setInt(3, entity.getId());
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
    public void insert(RoadCell newEntity) throws SQLException {
        String SQL = "insert into road_cell(car_id, road_id) values(?, ?)";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, null);
            if (newEntity.getCar() != null) {
                ps.setInt(1, newEntity.getCar().getId());
            }
            ps.setInt(2, newEntity.getRoadId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public void findAndClear(Car entity) {
        List<RoadCell> cells = getAll();
        if (!cells.isEmpty()) {
            for (RoadCell cell : cells) {
                if (cell.getCar()!= null) {
                    if (cell.getCar().getId().equals(entity.getId())) {
                        cell.setCar(null);
                        update(cell);
                    }
                }
            }
        }
    }

    @Override
    public void clear() {
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            String SQL = "delete from road_cell";
            st.execute(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
