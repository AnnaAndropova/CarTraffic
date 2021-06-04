package Task1.domain.entity;

import Task1.component.CarConverter;

import javax.persistence.*;

@Entity
@Table(name = "road_cell")
public class RoadCell {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @Convert(converter = CarConverter.class)
    private Car car;

    @Column(name = "road_id")
    private Integer roadId;

    public Integer getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }


    public void setCar() {
        this.car = null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    @Override
    public String toString() {
        return "RoadCell{" +
                "id=" + id +
                ", car=" + car +
                ", roadId=" + roadId +
                '}';
    }
}
