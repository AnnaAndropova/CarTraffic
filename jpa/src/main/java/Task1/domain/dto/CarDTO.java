package Task1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO implements Comparable<CarDTO> {

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

    public void setId(Integer id) {
        this.id = id;
    }

    public CarTypeEnum getType() {
        return type;
    }

    public void setType(CarTypeEnum type) {
        this.type = type;
    }

    public Integer getNormalSpeed() {
        return normalSpeed;
    }

    public void setNormalSpeed(Integer normalSpeed) {
        this.normalSpeed = normalSpeed;
    }

    public Integer getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(Integer curSpeed) {
        this.curSpeed = curSpeed;
    }

    public CarStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CarStatusEnum status) {
        this.status = status;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTimeHardSlowing() {
        return timeHardSlowing;
    }

    public void setTimeHardSlowing(Integer timeHardSlowing) {
        this.timeHardSlowing = timeHardSlowing;
    }

    public Integer getTimeCrashed() {
        return timeCrashed;
    }

    public void setTimeCrashed(Integer timeCrashed) {
        this.timeCrashed = timeCrashed;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
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

    @Override
    public int compareTo(CarDTO dto) {
        if (this.id > dto.getId()) {
            return 1;
        } else if (this.id == dto.getId()) {
            return 0;
        }
        return -1;
    }
}
