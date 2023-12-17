package Backend.entity;

import Backend.audit.Auditable;
import Backend.new_entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "parcel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "estimate_received_date")
//    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss a")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date received_date;

    @ManyToOne
    @JoinColumn(name = "start_location_id")
    private Location startLocation;

    @ManyToOne
    @JoinColumn(name = "end_location_id")
    private Location endLocation;

    @Column(name = "status")
    private int status;

    @Column(name = "canceled")
    private boolean canceled = false;

    @Column(name = "weight")
    private int weight;
}
