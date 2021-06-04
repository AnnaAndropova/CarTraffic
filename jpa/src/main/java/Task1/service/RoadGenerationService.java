package Task1.service;

import Task1.component.RoadComponent;
import Task1.domain.dto.RoadCellDTO;
import Task1.domain.dto.RoadDTO;
import Task1.domain.entity.Road;
import Task1.component.mapper.MainMapper;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoadGenerationService {

    private final RoadRepository roadRepository;
    private final MainMapper mapper;
    private final RoadCellRepository roadCellRepository;
    private final RoadComponent roadComponent;

    @Autowired
    public RoadGenerationService(RoadRepository roadRepository, MainMapper mapper, RoadCellRepository roadCellRepository, RoadComponent roadComponent) {
        this.roadRepository = roadRepository;
        this.mapper = mapper;
        this.roadCellRepository = roadCellRepository;
        this.roadComponent = roadComponent;
    }

    public void init() {
        RoadDTO roadDTO = new RoadDTO();
        roadDTO.setLength(roadComponent.getRoadLength());
        roadRepository.insert(mapper.roadDto2road(roadDTO));
        Road cur = roadRepository.getAll().get(0);
        for (int i = 0; i < cur.getLength(); i++) {
            RoadCellDTO roadCellDTO = new RoadCellDTO();
            roadCellDTO.setRoadId(cur.getId());
            roadCellDTO.setCar(null);
            roadCellRepository.insert(mapper.roadCellDto2roadCell(roadCellDTO));
        }
    }
}
