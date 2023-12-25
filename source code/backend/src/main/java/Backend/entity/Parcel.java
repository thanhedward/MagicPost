package Backend.entity;

import Backend.utilities.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "parcel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parcel_id")
    private Long id;

    @Column(name = "name")
    private String name;

    //TODO: Add info of sender (phone,...; for invoice purpose). May create a new User entity
    @Column(name = "sender_name")
    private String sender;

    @Column(name = "start_address")
    private String startAddress;

    @Column(name = "end_address")
    private String endAddress;

    //TODO: Add type of parcel (mail/goods...). Different type may have different fee
    //TODO: Convert distance of province/district to estimate arrive time

    @ManyToOne
    @JoinColumn(name = "start_post_office_id")
    private PostOffice startPostOffice;

    @ManyToOne
    @JoinColumn(name = "end_post_office_id")
    private PostOffice endPostOffice;

    @ManyToOne
    @JoinColumn(name = "start_depot_id")
    private Depot startDepot;

    @ManyToOne
    @JoinColumn(name = "end_depot_id")
    private Depot endDepot;

    @ManyToOne
    @JoinColumn(name = "accepted_by")
    private User acceptedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParcelStatus status;

    @Column(name = "weight")
    private int weight;

    @ManyToMany(mappedBy = "parcels")
    private Set<Invoice> invoices;
}