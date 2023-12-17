package Backend.new_entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "depot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depot_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "province")
    private Province province;

    //TODO: Add more info of Depot (name, phone, address...)
}
