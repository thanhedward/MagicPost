package Backend.repository;

import Backend.entity.Parcels;
import Backend.utilities.ParcelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelsRepository extends JpaRepository<Parcels, Long> {
    List<Parcels> findAllByAcceptedBy_Username(String username);

    List<Parcels> findAllByStatus(ParcelStatus parcelStatus);

}
