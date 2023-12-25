package Backend.entity;

import Backend.utilities.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_office_id")
    private PostOffice postOffice;

    @ManyToOne
    @JoinColumn(name = "first_depot")
    private Depot firstDepot;

    @ManyToOne
    @JoinColumn(name = "second_depot")
    private Depot secondDepot;

    @Column(name = "end_address")
    private String endAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InvoiceType type;

    @CreationTimestamp
    private LocalDateTime createdDate;

    // Set when confirm
    @Column(name = "confirm_date")
    private LocalDateTime confirmDate;

    @Column(name = "confirmed", nullable = false)
    private boolean confirmed = false;

    @ManyToOne
    @CreatedBy
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "confirm_by")
    private User confirmBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "parcel_invoice",
            joinColumns = {@JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "parcel_id", referencedColumnName = "parcel_id")})
    private Set<Parcel> parcels;
}
