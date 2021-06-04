package Task1.component.mapper;

import Task1.component.CarConverter;
import Task1.domain.dto.CarDTO;
import Task1.domain.dto.RoadCellDTO;
import Task1.domain.dto.RoadDTO;
import Task1.domain.entity.Car;
import Task1.domain.entity.Road;
import Task1.domain.entity.RoadCell;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public class MainMapper {

    @Autowired
    private CarConverter carConverter;

    public Car carDto2car(CarDTO dto) {
        Car car = new Car();
        car.setId(dto.getId());
        car.setType(dto.getType());
        car.setCurSpeed((dto.getCurSpeed()));
        car.setNormalSpeed(dto.getNormalSpeed());
        car.setStatus(dto.getStatus());
        car.setPosition(dto.getPosition());
        car.setTimeHardSlowing(dto.getTimeHardSlowing());
        car.setTimeCrashed(dto.getTimeCrashed());

        return car;
    }

    public CarDTO car2carDto(Car entity) {
        CarDTO dto = new CarDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setCurSpeed(entity.getCurSpeed());
        dto.setNormalSpeed(entity.getNormalSpeed());
        dto.setStatus(entity.getStatus());
        dto.setPosition(entity.getPosition());
        dto.setTimeHardSlowing(entity.getTimeHardSlowing());
        dto.setTimeCrashed(entity.getTimeCrashed());

        return dto;
    }

    public List<Car> carDto2car(List<CarDTO> dto) {
        List<Car> res = new ArrayList<>();
        for (CarDTO el : dto) {
            res.add(carDto2car(el));
        }
        return res;
    }

    public List<CarDTO> car2carDto(List<Car> entity) {
        List<CarDTO> res = new ArrayList<>();
        for (Car el : entity) {
            res.add(car2carDto(el));
        }
        return res;
    }

    public RoadCell roadCellDto2roadCell(RoadCellDTO dto) {
        RoadCell roadCell = new RoadCell();
        roadCell.setId(dto.getId());
        roadCell.setRoadId(dto.getRoadId());

        if (dto.getCar() != null) {
            roadCell.setCar(carConverter.convertToEntityAttribute(dto.getCar()));
        }

        return roadCell;
    }

    public RoadCellDTO roadCell2roadCellDto(RoadCell entity) {
        RoadCellDTO dto = new RoadCellDTO();
        dto.setId(entity.getId());
        dto.setRoadId(entity.getRoadId());

        if (entity.getCar() != null) {
            dto.setCar(carConverter.convertToDatabaseColumn(entity.getCar()));
        }

        return dto;
    }

    public List<RoadCell> roadCellDto2roadCell(List<RoadCellDTO> dto) {
        List<RoadCell> res = new ArrayList<>();

            for (RoadCellDTO el : dto) {
                RoadCell roadCell = roadCellDto2roadCell(el);
                res.add(roadCell);
            }

        return res;
    }

    public List<RoadCellDTO> roadCell2roadCellDto(List<RoadCell> entity) {
        List<RoadCellDTO> res = new ArrayList<>();
        for (RoadCell el : entity) {
            RoadCellDTO dto = roadCell2roadCellDto(el);
            res.add(dto);
        }
        return res;
    }

    public Road roadDto2road(RoadDTO dto) {
        Road road = new Road();
        road.setId(dto.getId());
        road.setLength(dto.getLength());

        return road;
    }

    public RoadDTO road2roadDto(Road entity) {
        RoadDTO dto = new RoadDTO();
        dto.setId(entity.getId());
        dto.setLength(entity.getLength());

        return dto;
    }
}
