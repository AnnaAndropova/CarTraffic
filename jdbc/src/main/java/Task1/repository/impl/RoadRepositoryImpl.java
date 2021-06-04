package Task1.repository.impl;

import Task1.component.mapper.RoadRowMapper;
import Task1.model.Road;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoadRepositoryImpl implements RoadRepository {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoadRowMapper roadRowMapper;

    @Override
    public Road get(Long id) throws SQLException {
        String SQL = "select * from road where id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            List<Road> roads = new ArrayList<>();
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id");
                Integer length = resultSet.getInt("length");
                roads.add(new Road(id1, length));
            }
            return roads.get(0);
        }
    }


    @Override
    public List<Road> getAll() {
        List<Road> roads = jdbcTemplate.query("select * from road", roadRowMapper);
        return roads;
    }

    @Override
    public void update(Road road) throws SQLException {
        String SQL = "update road set length=? where id=?";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, road.getLength());
            ps.setInt(2, road.getId());
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
        }
    }

    @Override
    public void insert(Road newRoad) throws SQLException {
        String SQL = "insert into road(length) values(?)";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, newRoad.getLength());
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
    public void clear() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            String SQL = "delete from road";
            st.execute(SQL);
        }
    }
}
