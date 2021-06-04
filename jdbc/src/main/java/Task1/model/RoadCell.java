package Task1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
public class RoadCell {

    @Id
    private Integer id;

    private Car car;

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
