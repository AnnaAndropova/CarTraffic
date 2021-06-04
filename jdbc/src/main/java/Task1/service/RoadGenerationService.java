package Task1.service;

import Task1.component.RoadComponent;
import Task1.model.Road;
import Task1.model.RoadCell;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class RoadGenerationService {

    private final RoadRepository roadRepository;
    private final RoadCellRepository roadCellRepository;
    private final RoadComponent roadComponent;

    @Autowired
    public RoadGenerationService(RoadRepository roadRepository, RoadCellRepository roadCellRepository, RoadComponent roadComponent) {
        this.roadRepository = roadRepository;
        this.roadCellRepository = roadCellRepository;
        this.roadComponent = roadComponent;
    }

    public void init() {
        Road road = new Road();
        road.setLength(roadComponent.getRoadLength());
        try {
            roadRepository.insert(road);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Road cur = roadRepository.getAll().get(0);
        for (int i = 0; i < cur.getLength(); i++) {
            RoadCell roadCell = new RoadCell();
            roadCell.setRoadId(cur.getId());
            roadCell.setCar(null);
            try {
                roadCellRepository.insert(roadCell);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
