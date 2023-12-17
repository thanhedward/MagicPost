package Backend.repository;

import Backend.entity.Parcels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelsRepository extends JpaRepository<Parcels, Long> {
//    List<Parcels> findAllByAcceptedBy(String user_name);

}
