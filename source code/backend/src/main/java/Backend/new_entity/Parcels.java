package Backend.new_entity;

import Backend.utilities.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "parcels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcels implements Serializable {

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

    @Column(name = "status")
    private ParcelStatus status;

    @Column(name = "weight")
    private int weight;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "parcel_invoice",
            joinColumns = {@JoinColumn(name = "parcel_id", referencedColumnName = "parcel_id")},
            inverseJoinColumns = {@JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")})
    private Set<Invoice> invoices;
}