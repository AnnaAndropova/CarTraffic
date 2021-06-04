package Task1.component;

import Task1.domain.entity.Car;
import Task1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.PersistenceContext;

@Convert
@Component
public class CarConverter implements AttributeConverter<Car, Integer> {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Integer convertToDatabaseColumn(Car car) {
        return car.getId();
    }

    @Override
    public Car convertToEntityAttribute(Integer integer) {
        return carRepository.get(integer).isPresent() ? carRepository.get(integer).get() : null;
    }
}
