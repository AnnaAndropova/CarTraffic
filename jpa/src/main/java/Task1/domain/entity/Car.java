package Task1.domain.entity;

import Task1.domain.dto.CarStatusEnum;
import Task1.domain.dto.CarTypeEnum;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "car")
@NoArgsConstructor
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CarTypeEnum type;

    @Column(name = "normal_speed")
    private Integer normalSpeed;

    @Column(name = "cur_speed")
    private Integer curSpeed;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CarStatusEnum status;

    @Column(name = "position")
    private Integer position;

    @Column(name = "time_hard_slowing")
    private Integer timeHardSlowing;

    @Column(name = "time_crashed")
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
}
