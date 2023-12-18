package Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "distance_province")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceProvince {
    @EmbeddedId
    private ProvinceID provinceID;

    @Column(name = "distance")
    private Long distance;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class ProvinceID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "first_province")
    private Province firstProvince;

    @ManyToOne
    @JoinColumn(name = "second_province")
    private Province secondProvince;
}