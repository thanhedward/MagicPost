package Backend.repository;

import Backend.entity.Depot;
import Backend.entity.Parcel;
import Backend.entity.PostOffice;
import Backend.utilities.ParcelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelsRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findAllByStatusAndStartPostOffice(ParcelStatus parcelStatus, PostOffice postOffice);
    List<Parcel> findAllByStatusAndStartDepotOrderByEndDepotAsc(ParcelStatus parcelStatus, Depot depot);
    List<Parcel> findAllByStatusAndEndDepotOrderByEndPostOfficeAsc(ParcelStatus parcelStatus, Depot depot);
    List<Parcel> findAllByStatusAndEndPostOffice(ParcelStatus parcelStatus, PostOffice postOffice);

    List<Parcel> findAllByStatus(ParcelStatus parcelStatus);
}
