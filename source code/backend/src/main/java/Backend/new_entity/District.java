package Backend.new_entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "district")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;

    @Column(name = "name")
    private String district;

    @ManyToOne
    @JoinColumn(name = "province")
    private Province province;

    @Column(name = "distance_to_province")
    private Long distanceToProvince;
}
