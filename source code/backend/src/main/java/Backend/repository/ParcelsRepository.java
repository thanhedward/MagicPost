package Backend.repository;

import Backend.entity.Parcel;
import Backend.utilities.ParcelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelsRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findAllByAcceptedBy_Username(String username);

    List<Parcel> findAllByStatus(ParcelStatus parcelStatus);

//    void add_invoice

}
