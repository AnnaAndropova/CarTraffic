package Task1.component;

import Task1.domain.entity.RoadCell;
import Task1.repository.RoadCellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
@Component
public class RoadCellConverter implements AttributeConverter<RoadCell, Integer> {

    @Autowired
    private RoadCellRepository roadCellRepository;

    @Override
    public Integer convertToDatabaseColumn(RoadCell roadCell) {
        return roadCell.getId();
    }

    @Override
    public RoadCell convertToEntityAttribute(Integer integer) {
        return roadCellRepository.get(integer);
    }
}
