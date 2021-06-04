package Task1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    private Integer id;
    private CarTypeEnum type;
    private Integer normalSpeed;
    private Integer curSpeed;
    private CarStatusEnum status;
    private Integer position;
    private Integer timeHardSlowing;
    private Integer timeCrashed;

    public Integer getId() {
        return id;
    }

    public CarTypeEnum getType() {
        return type;
    }

    public Integer getNormalSpeed() {
        return normalSpeed;
    }

    public Integer getCurSpeed() {
        return curSpeed;
    }

    public CarStatusEnum getStatus() {
        return status;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getTimeHardSlowing() {
        return timeHardSlowing;
    }

    public Integer getTimeCrashed() {
        return timeCrashed;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(CarTypeEnum type) {
        this.type = type;
    }

    public void setNormalSpeed(Integer normalSpeed) {
        this.normalSpeed = normalSpeed;
    }

    public void setCurSpeed(Integer curSpeed) {
        this.curSpeed = curSpeed;
    }

    public void setStatus(CarStatusEnum status) {
        this.status = status;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setTimeHardSlowing(Integer timeHardSlowing) {
        this.timeHardSlowing = timeHardSlowing;
    }

    public void setTimeCrashed(Integer timeCrashed) {
        this.timeCrashed = timeCrashed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", type=" + type +
                ", normalSpeed=" + normalSpeed +
                ", curSpeed=" + curSpeed +
                ", status=" + status +
                ", position=" + position +
                ", timeHardSlowing=" + timeHardSlowing +
                ", timeCrashed=" + timeCrashed +
                '}';
    }

    public int compareTo(Car car) {
        if (this.id > car.getId()) {
            return 1;
        } else if (this.id == car.getId()) {
            return 0;
        }
        return -1;
    }
}
