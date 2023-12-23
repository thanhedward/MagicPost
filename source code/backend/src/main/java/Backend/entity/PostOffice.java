package Backend.entity;

import Backend.entity.Depot;
import Backend.entity.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post_office")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOffice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_office_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "depot")
    private Depot depot;

    //TODO: Add more info of Post Office (name, phone, address...)
}
