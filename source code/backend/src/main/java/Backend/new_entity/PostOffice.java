package Backend.new_entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
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

    @ManyToOne
    @JoinColumn(name = "district")
    private District district;

    @ManyToOne
    @JoinColumn(name = "depot")
    private Depot depot;

    //TODO: Add more info of Post Office (name, phone, address...)
}
