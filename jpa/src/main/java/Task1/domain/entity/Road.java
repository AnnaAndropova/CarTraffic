package Task1.domain.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "road")
@NoArgsConstructor
public class Road {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "length")
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
