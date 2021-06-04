package Task1;

import Task1.component.CarComponent;
import Task1.config.DumbConfig;
import Task1.domain.dto.CarDTO;
import Task1.domain.entity.Car;
import Task1.component.mapper.MainMapper;
import Task1.repository.CarRepository;
import Task1.repository.impl.CarRepositoryImpl;
import Task1.service.CarGenerationService;
import Task1.service.TrafficService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {CarComponent.class, DumbConfig.class, CarDTO.class, Car.class, MainMapper.class, CarRepository.class, CarRepositoryImpl.class, CarGenerationService.class})
public class JpaApp {


    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaApp.class);
        //ApplicationConfig appConfig = context.getBean(ApplicationConfig.class);

        TrafficService ts = context.getBean(TrafficService.class);
        ts.run();




        // ЭТО РАБОТАЕТ
//        RoadCellRepositoryImpl r = context.getBean(RoadCellRepositoryImpl.class);
//        CarRepositoryImpl cr = context.getBean(CarRepositoryImpl.class);
//        MainMapper m =context.getBean(MainMapper.class);
//        List<RoadCell> c = r.getAll();
//        for (RoadCell cc: c){
//            System.out.println(cc.toString());
//        }
//        RoadCell c1 = c.get(10);
//        Car car = new Car();
//        car.setType(CarTypeEnum.PASSENGER);
//        car.setTimeCrashed(0);
//        car.setNormalSpeed(0);
//        car.setPosition(0);
//        car.setCurSpeed(0);
//        car.setStatus(CarStatusEnum.MOVING);
//        car.setTimeHardSlowing(0);
//        //cr.insert(car);
//        car = cr.get(23).get();
//        c1.setCar(car);
//        r.update(c1);
//        c1 = r.get(5414);
//        System.out.println("***********" + c1.toString());
//        System.out.println(c1.getCar().toString());

//        Car car = new Car();
//        car.setType(CarTypeEnum.PASSENGER);
//        car.setTimeCrashed(0);
//        car.setNormalSpeed(0);
//        car.setPosition(0);
//        car.setCurSpeed(0);
//        car.setStatus(CarStatusEnum.MOVING);
//        car.setTimeHardSlowing(0);
//        cr.insert(car);
//
//        CarDTO car1 = new CarDTO();
//        car1.setId(128);
//        if (cr.get(car1.getId()).isPresent()) {
//            Car c = cr.get(car1.getId()).get();
//            //System.out.println(c.toString());
//            if (cr.getPrev(cr.get(car1.getId()).get()) != null) {
//                System.out.println(m.car2carDto(cr.getPrev(cr.get(car1.getId()).get())).toString());
//            }
//        }
    }

}
