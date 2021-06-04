package Task1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
public class Road {

    @Id
    private Integer id;
    private Integer length;

    public Integer getId() {
        return id;
    }

    public Integer getLength() {
        return length;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
